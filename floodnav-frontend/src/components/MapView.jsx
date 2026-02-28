import { APIProvider, InfoWindow, Map, Marker, useMap } from '@vis.gl/react-google-maps';
import { useEffect, useState } from 'react';
import {
  DEFAULT_CENTER,
  DEFAULT_ZOOM,
  GRAPH_EDGES,
  GRAPH_NODES,
  getSeverityColor
} from '../utils/mapUtils';

const GOOGLE_MAPS_API_KEY = "AIzaSyApw7jKl2M8XzOlU9rSm1ERmw4hPIOu2gk";

/* -------------------- Polyline Helper -------------------- */
function MapPolyline({ path, options }) {
  const map = useMap();

  useEffect(() => {
    if (!map || !path || path.length === 0 || !window.google) return;

    const polyline = new window.google.maps.Polyline({
      path,
      map,
      ...options,
    });

    return () => {
      polyline.setMap(null);
    };
  }, [map, path, options]);

  return null;
}

/* -------------------- Map Content (renders after API loads) -------------------- */
function MapContent({ sosRequests, route, blockedRoads, showGraph, onMapClick, rescueBases, onLocationSelect }) {
  const map = useMap();
  const [apiReady, setApiReady] = useState(false);
  const [selectedMarker, setSelectedMarker] = useState(null);

  useEffect(() => {
    if (map && window.google?.maps?.SymbolPath) {
      setApiReady(true);
    }
  }, [map]);

  if (!apiReady) return null;

  return (
    <>
      {/* ---------------- Graph Nodes ---------------- */}
      {showGraph &&
        GRAPH_NODES.map((node) => (
          <Marker
            key={node.id}
            position={{ lat: node.lat, lng: node.lng }}
            icon={{
              path: window.google.maps.SymbolPath.CIRCLE,
              scale: 3,
              fillColor: '#2562f0ff',
              fillOpacity: 0.9,
              strokeColor: '#064ffaff',
              strokeWeight: 1,
            }}
            title={node.name}
            onClick={() => {
              setSelectedMarker({ type: 'node', data: node });
              if (onLocationSelect) {
                onLocationSelect({ lat: node.lat, lng: node.lng });
              }
            }}
          />
        ))}

      {/* ---------------- Graph Edges ---------------- */}
      {showGraph &&
        GRAPH_EDGES.map(([nodeId1, nodeId2], index) => {
          const node1 = GRAPH_NODES.find(n => n.id === nodeId1);
          const node2 = GRAPH_NODES.find(n => n.id === nodeId2);

          if (!node1 || !node2) return null;

          const isBlocked = blockedRoads.some(
            block =>
              (block.startNode === nodeId1 && block.endNode === nodeId2) ||
              (block.startNode === nodeId2 && block.endNode === nodeId1)
          );

          return (
            <MapPolyline
              key={`edge-${index}`}
              path={[
                { lat: node1.lat, lng: node1.lng },
                { lat: node2.lat, lng: node2.lng },
              ]}
              options={{
                strokeColor: isBlocked ? '#ef4444' : '#5089ea',
                strokeWeight: isBlocked ? 6 : 6,
                strokeOpacity: isBlocked ? 0.8 : 0.8,
              }}
            />
          );
        })}

      {/* ---------------- SOS Requests ---------------- */}
      {sosRequests.map((request) => (
        <Marker
          key={request.id}
          position={{ lat: request.latitude, lng: request.longitude }}
          icon={{
            path: window.google.maps.SymbolPath.CIRCLE,
            scale: 10,
            fillColor: getSeverityColor(request.severityLevel),
            fillOpacity: 0.9,
            strokeColor: '#ffffff',
            strokeWeight: 2,
          }}
          onClick={() => setSelectedMarker({ type: 'sos', data: request })}
        />
      ))}

      {/* ---------------- Rescue Bases ---------------- */}
      {rescueBases && rescueBases.map((base) => (
        <Marker
          key={`base-${base.id}`}
          position={{ lat: base.latitude, lng: base.longitude }}
          icon={{
            path: window.google.maps.SymbolPath.CIRCLE,
            scale: 10,
            fillColor: '#22c55e',
            fillOpacity: 1,
            strokeColor: '#ffffff',
            strokeWeight: 3,
          }}
          title={base.name}
          onClick={() => setSelectedMarker({ type: 'base', data: base })}
        />
      ))}

      {/* ---------------- Route ---------------- */}
      {route && route.length > 0 && (
        <>
          <MapPolyline
            path={route.map(coord => ({
              lat: coord.latitude,
              lng: coord.longitude,
            }))}
            options={{
              strokeColor: '#22c55e',
              strokeWeight: 8,
              strokeOpacity: 1,
              zIndex: 10,
            }}
          />

          {/* Start Marker */}
          <Marker
            position={{
              lat: route[0].latitude,
              lng: route[0].longitude,
            }}
            icon={{
              url: 'https://maps.google.com/mapfiles/ms/icons/green-dot.png',
            }}
            title="Rescue Base"
          />

          {/* End Marker */}
          <Marker
            position={{
              lat: route[route.length - 1].latitude,
              lng: route[route.length - 1].longitude,
            }}
            icon={{
              url: 'https://maps.google.com/mapfiles/ms/icons/red-dot.png',
            }}
            title="Rescue Destination"
          />
        </>
      )}

      {/* ---------------- Info Window ---------------- */}
      {selectedMarker && (
        <InfoWindow
          position={
            selectedMarker.type === 'node'
              ? {
                  lat: selectedMarker.data.lat,
                  lng: selectedMarker.data.lng,
                }
              : {
                  lat: selectedMarker.data.latitude,
                  lng: selectedMarker.data.longitude,
                }
          }
          onCloseClick={() => setSelectedMarker(null)}
        >
          <div className="p-2">
            {selectedMarker.type === 'node' ? (
              <>
                <h3 className="font-bold text-gray-900">
                  {selectedMarker.data.name}
                </h3>
                <p className="text-sm text-gray-600">
                  Node ID: {selectedMarker.data.id}
                </p>
              </>
            ) : selectedMarker.type === 'base' ? (
              <>
                <h3 className="font-bold text-green-700">
                  🏥 {selectedMarker.data.name}
                </h3>
                <p className="text-sm text-gray-600">
                  Rescue Base
                </p>
                <p className="text-sm text-gray-600">
                  Available Teams: {selectedMarker.data.availableTeams}
                </p>
              </>
            ) : (
              <>
                <h3 className="font-bold text-gray-900 mb-1">
                  SOS Request #{selectedMarker.data.id}
                </h3>
                <div className="space-y-1 text-sm">
                  <p>
                    <span className="font-semibold">People:</span>{' '}
                    {selectedMarker.data.affectedPeople}
                  </p>
                  <p>
                    <span className="font-semibold">Severity:</span>{' '}
                    <span
                      className="px-2 py-1 rounded text-white"
                      style={{
                        backgroundColor: getSeverityColor(
                          selectedMarker.data.severityLevel
                        ),
                      }}
                    >
                      {selectedMarker.data.severityLevel}
                    </span>
                  </p>
                  {selectedMarker.data.notes && (
                    <p>
                      <span className="font-semibold">Notes:</span>{' '}
                      {selectedMarker.data.notes}
                    </p>
                  )}
                </div>
              </>
            )}
          </div>
        </InfoWindow>
      )}
    </>
  );
}

/* -------------------------------------------------------- */

export default function MapView({
  sosRequests = [],
  route = null,
  blockedRoads = [],
  showGraph = false,
  onMapClick = null,
  center = DEFAULT_CENTER,
  zoom = DEFAULT_ZOOM,
  rescueBases = [],
  onLocationSelect = null
}) {
  if (!GOOGLE_MAPS_API_KEY || GOOGLE_MAPS_API_KEY === 'YOUR_GOOGLE_MAPS_API_KEY_HERE') {
    return (
      <div className="w-full h-full bg-gray-100 rounded-lg flex items-center justify-center">
        <div className="text-center p-6">
          <p className="text-red-600 font-semibold mb-2">
            Google Maps API Key Missing
          </p>
          <p className="text-gray-600 text-sm">
            Please add your Google Maps API key to the .env file
          </p>
        </div>
      </div>
    );
  }

  return (
    <APIProvider apiKey={GOOGLE_MAPS_API_KEY}>
      <Map
        defaultCenter={center}
        defaultZoom={zoom}
        mapId="floodnav-map"
        onClick={onMapClick}
        className="w-full h-full rounded-lg"
        disableDefaultUI={false}
        zoomControl
        streetViewControl={false}
        mapTypeControl
      >
        <MapContent
          sosRequests={sosRequests}
          route={route}
          blockedRoads={blockedRoads}
          showGraph={showGraph}
          onMapClick={onMapClick}
          rescueBases={rescueBases}
          onLocationSelect={onLocationSelect}
        />
      </Map>
    </APIProvider>
  );
}
