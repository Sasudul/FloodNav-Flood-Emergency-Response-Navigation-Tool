import { AlertTriangle, CheckCircle, MapPin, Send, Users, XCircle } from 'lucide-react';
import { useEffect, useState } from 'react';
import MapView from '../components/MapView';
import { getBlockedRoads, getRescueBases, submitSosRequest } from '../services/api';
import { DEFAULT_CENTER } from '../utils/mapUtils';

export default function VictimSOS() {
  const [formData, setFormData] = useState({
    latitude: DEFAULT_CENTER.lat,
    longitude: DEFAULT_CENTER.lng,
    affectedPeople: 1,
    severityLevel: 'MEDIUM',
    notes: ''
  });

  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState(null);
  const [rescueBases, setRescueBases] = useState([]);
  const [blockedRoads, setBlockedRoads] = useState([]);

  useEffect(() => {
    const loadMapData = async () => {
      try {
        const [basesData, blockedData] = await Promise.all([
          getRescueBases(),
          getBlockedRoads()
        ]);
        setRescueBases(basesData.bases || []);
        setBlockedRoads(blockedData.blockedRoads || []);
      } catch (error) {
        console.error('Failed to load map data:', error);
      }
    };
    loadMapData();
  }, []);

  const handleMapClick = (e) => {
    if (e.detail && e.detail.latLng) {
      setFormData(prev => ({
        ...prev,
        latitude: e.detail.latLng.lat,
        longitude: e.detail.latLng.lng
      }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setResponse(null);

    try {
      const result = await submitSosRequest(formData);
      setResponse({ type: 'success', data: result });
      
      // Reset form after successful submission
      setTimeout(() => {
        setFormData({
          latitude: DEFAULT_CENTER.lat,
          longitude: DEFAULT_CENTER.lng,
          affectedPeople: 1,
          severityLevel: 'MEDIUM',
          notes: ''
        });
        setResponse(null);
      }, 5000);
    } catch (error) {
      setResponse({ 
        type: 'error', 
        message: error.response?.data?.message || 'Failed to submit SOS request' 
      });
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'affectedPeople' ? parseInt(value) || 0 : value
    }));
  };

  return (
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
      {/* Form Section */}
      <div className="space-y-6">
        <div className="card">
          <div className="flex items-center space-x-3 mb-6">
            <AlertTriangle className="w-8 h-8 text-red-600" />
            <div>
              <h2 className="text-2xl font-bold text-gray-900">Emergency SOS Request</h2>
              <p className="text-sm text-gray-600">Submit your location for rescue</p>
            </div>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            {/* Location */}
            <div>
              <label className="label">
                <MapPin className="w-4 h-4 inline mr-2" />
                Location (Click on map to set)
              </label>
              <div className="grid grid-cols-2 gap-2">
                <input
                  type="number"
                  name="latitude"
                  value={formData.latitude}
                  onChange={handleChange}
                  step="0.0001"
                  placeholder="Latitude"
                  className="input-field"
                  required
                />
                <input
                  type="number"
                  name="longitude"
                  value={formData.longitude}
                  onChange={handleChange}
                  step="0.0001"
                  placeholder="Longitude"
                  className="input-field"
                  required
                />
              </div>
            </div>

            {/* Affected People */}
            <div>
              <label className="label">
                <Users className="w-4 h-4 inline mr-2" />
                Number of People Affected
              </label>
              <input
                type="number"
                name="affectedPeople"
                value={formData.affectedPeople}
                onChange={handleChange}
                min="1"
                className="input-field"
                required
              />
            </div>

            {/* Severity Level */}
            <div>
              <label className="label">
                <AlertTriangle className="w-4 h-4 inline mr-2" />
                Severity Level
              </label>
              <select
                name="severityLevel"
                value={formData.severityLevel}
                onChange={handleChange}
                className="input-field"
                required
              >
                <option value="LOW">Low - Minor assistance needed</option>
                <option value="MEDIUM">Medium - Moderate danger</option>
                <option value="HIGH">High - Serious situation</option>
                <option value="CRITICAL">Critical - Life threatening</option>
              </select>
            </div>

            {/* Notes */}
            <div>
              <label className="label">Additional Notes</label>
              <textarea
                name="notes"
                value={formData.notes}
                onChange={handleChange}
                rows="3"
                placeholder="Describe your situation, injuries, or special needs..."
                className="input-field resize-none"
              />
            </div>

            {/* Submit Button */}
            <button
              type="submit"
              disabled={loading}
              className="btn-danger w-full flex items-center justify-center space-x-2"
            >
              {loading ? (
                <>
                  <div className="spinner w-5 h-5 border-2" />
                  <span>Sending SOS...</span>
                </>
              ) : (
                <>
                  <Send className="w-5 h-5" />
                  <span>Send SOS Request</span>
                </>
              )}
            </button>
          </form>

          {/* Response Messages */}
          {response && (
            <div className={`mt-4 p-4 rounded-lg flex items-start space-x-3 ${
              response.type === 'success' 
                ? 'bg-green-50 text-green-800 border border-green-200' 
                : 'bg-red-50 text-red-800 border border-red-200'
            }`}>
              {response.type === 'success' ? (
                <CheckCircle className="w-5 h-5 flex-shrink-0 mt-0.5" />
              ) : (
                <XCircle className="w-5 h-5 flex-shrink-0 mt-0.5" />
              )}
              <div>
                <p className="font-semibold">
                  {response.type === 'success' ? response.data.message : 'Error'}
                </p>
                {response.type === 'success' ? (
                  <p className="text-sm mt-1">
                    Request ID: #{response.data.requestId} | 
                    Priority Score: {response.data.priorityScore}
                  </p>
                ) : (
                  <p className="text-sm mt-1">{response.message}</p>
                )}
              </div>
            </div>
          )}
        </div>

        {/* Info Box */}
        <div className="card bg-blue-50 border border-blue-200">
          <h3 className="font-bold text-blue-900 mb-2">How It Works</h3>
          <ul className="text-sm text-blue-800 space-y-2">
            <li>• Click on the map to set your exact location</li>
            <li>• Fill in the number of people needing rescue</li>
            <li>• Select severity level based on your situation</li>
            <li>• Our algorithm will prioritize and route rescue teams</li>
            <li>• Help is on the way! Stay safe and wait for rescue</li>
          </ul>
        </div>
      </div>

      {/* Map Section */}
      <div className="h-[600px]">
        <MapView
          sosRequests={[formData]}
          onMapClick={handleMapClick}
          center={{ lat: formData.latitude, lng: formData.longitude }}
          showGraph={true}
          blockedRoads={blockedRoads}
          rescueBases={rescueBases}
          onLocationSelect={({ lat, lng }) => {
            setFormData(prev => ({ ...prev, latitude: lat, longitude: lng }));
          }}
        />
      </div>
    </div>
  );
}