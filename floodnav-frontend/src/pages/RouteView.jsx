import { AlertCircle, ChevronLeft, ChevronRight, Clock, MapPin, Navigation, Route } from 'lucide-react';
import { useEffect, useState } from 'react';
import ClusterCard from '../components/ClusterCard';
import MapView from '../components/MapView';
import { getBlockedRoads, getMissionHistory, getNextMission, getRescueBases } from '../services/api';
import { formatDistance } from '../utils/mapUtils';

export default function RouteView() {
  const [missionHistory, setMissionHistory] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(-1);
  const [loading, setLoading] = useState(false);
  const [loadingHistory, setLoadingHistory] = useState(true);
  const [error, setError] = useState(null);
  const [blockedRoads, setBlockedRoads] = useState([]);
  const [rescueBases, setRescueBases] = useState([]);

  // Load map data and mission history on mount
  useEffect(() => {
    const loadData = async () => {
      try {
        const [blockedData, basesData, historyData] = await Promise.all([
          getBlockedRoads(),
          getRescueBases(),
          getMissionHistory()
        ]);
        setBlockedRoads(blockedData.blockedRoads || []);
        setRescueBases(basesData.bases || []);

        // Load mission history (comes in desc order, reverse to asc for display)
        const missions = (historyData.missions || []).reverse().map((m, i) => ({
          ...m,
          missionNumber: i + 1,
          calculatedAt: m.createdAt
            ? new Date(m.createdAt).toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', second: '2-digit' })
            : '—'
        }));

        setMissionHistory(missions);
        if (missions.length > 0) {
          setCurrentIndex(missions.length - 1); // show most recent
        }
      } catch (err) {
        console.error('Failed to load data:', err);
      } finally {
        setLoadingHistory(false);
      }
    };
    loadData();
  }, []);

  const missionData = currentIndex >= 0 ? missionHistory[currentIndex] : null;

  const handleGetNextMission = async () => {
    setLoading(true);
    setError(null);

    try {
      const data = await getNextMission();
      
      if (data.status === 'SUCCESS') {
        const mission = {
          ...data,
          missionNumber: missionHistory.length + 1,
          calculatedAt: new Date().toLocaleTimeString('en-US', {
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
          })
        };
        setMissionHistory(prev => [...prev, mission]);
        setCurrentIndex(missionHistory.length);
      } else if (data.status === 'NO_MISSIONS') {
        setError({ type: 'info', message: data.message });
      } else if (data.status === 'NO_PATH') {
        setError({ type: 'warning', message: data.message });
      }
    } catch (err) {
      setError({ 
        type: 'error', 
        message: err.response?.data?.message || 'Failed to calculate rescue route' 
      });
    } finally {
      setLoading(false);
    }
  };

  const goToPreviousMission = () => {
    if (currentIndex > 0) {
      setCurrentIndex(currentIndex - 1);
      setError(null);
    }
  };

  const goToNextMission = () => {
    if (currentIndex < missionHistory.length - 1) {
      setCurrentIndex(currentIndex + 1);
      setError(null);
    }
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="card">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <Route className="w-8 h-8 text-blue-600" />
            <div>
              <h2 className="text-2xl font-bold text-gray-900">Rescue Route Calculator</h2>
              <p className="text-sm text-gray-600">
                AI-powered routing using Dijkstra's algorithm and Max Heap prioritization
              </p>
            </div>
          </div>
          
          <button
            onClick={handleGetNextMission}
            disabled={loading}
            className="btn-primary flex items-center space-x-2"
          >
            {loading ? (
              <>
                <div className="spinner w-5 h-5 border-2" />
                <span>Calculating...</span>
              </>
            ) : (
              <>
                <Navigation className="w-5 h-5" />
                <span>Calculate Next Mission</span>
              </>
            )}
          </button>
        </div>
      </div>

      {/* Loading History */}
      {loadingHistory && (
        <div className="card text-center py-8">
          <div className="spinner w-8 h-8 border-3 mx-auto mb-3" />
          <p className="text-gray-500">Loading mission history...</p>
        </div>
      )}

      {/* Mission History Navigation */}
      {missionHistory.length > 0 && (
        <div className="card">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <Clock className="w-5 h-5 text-gray-500" />
              <span className="text-sm font-medium text-gray-700">
                Mission History ({missionHistory.length} total)
              </span>
            </div>

            <div className="flex items-center space-x-2">
              {/* Previous Button */}
              <button
                onClick={goToPreviousMission}
                disabled={currentIndex <= 0}
                className={`flex items-center space-x-1 px-3 py-1.5 rounded-lg text-sm font-medium transition-colors ${
                  currentIndex > 0
                    ? 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                    : 'bg-gray-50 text-gray-300 cursor-not-allowed'
                }`}
              >
                <ChevronLeft className="w-4 h-4" />
                <span>Previous</span>
              </button>

              {/* Mission Indicator - show up to 10, with scroll if more */}
              <div className="flex items-center space-x-1 max-w-[320px] overflow-x-auto py-1">
                {missionHistory.map((_, i) => (
                  <button
                    key={i}
                    onClick={() => { setCurrentIndex(i); setError(null); }}
                    className={`w-8 h-8 rounded-full text-xs font-bold transition-colors flex-shrink-0 ${
                      i === currentIndex
                        ? 'bg-blue-600 text-white'
                        : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
                    }`}
                  >
                    {i + 1}
                  </button>
                ))}
              </div>

              {/* Next Button */}
              <button
                onClick={goToNextMission}
                disabled={currentIndex >= missionHistory.length - 1}
                className={`flex items-center space-x-1 px-3 py-1.5 rounded-lg text-sm font-medium transition-colors ${
                  currentIndex < missionHistory.length - 1
                    ? 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                    : 'bg-gray-50 text-gray-300 cursor-not-allowed'
                }`}
              >
                <span>Next</span>
                <ChevronRight className="w-4 h-4" />
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Error/Info Messages */}
      {error && (
        <div className={`card ${
          error.type === 'info' ? 'bg-blue-50 border-blue-200' :
          error.type === 'warning' ? 'bg-yellow-50 border-yellow-200' :
          'bg-red-50 border-red-200'
        } border`}>
          <div className="flex items-start space-x-3">
            <AlertCircle className={`w-5 h-5 flex-shrink-0 mt-0.5 ${
              error.type === 'info' ? 'text-blue-600' :
              error.type === 'warning' ? 'text-yellow-600' :
              'text-red-600'
            }`} />
            <p className={`${
              error.type === 'info' ? 'text-blue-800' :
              error.type === 'warning' ? 'text-yellow-800' :
              'text-red-800'
            }`}>{error.message}</p>
          </div>
        </div>
      )}

      {/* Mission Data */}
      {missionData && (
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Left - Route Stats */}
          <div className="space-y-4">
            {/* Mission Badge */}
            <div className="card bg-gradient-to-r from-blue-600 to-indigo-600 text-white">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-blue-100 text-xs font-medium uppercase tracking-wide">Mission</p>
                  <p className="text-3xl font-bold">#{missionData.missionNumber}</p>
                </div>
                <div className="text-right">
                  <p className="text-blue-100 text-xs font-medium">Calculated at</p>
                  <p className="text-sm font-semibold">{missionData.calculatedAt}</p>
                </div>
              </div>
              {missionData.message && (
                <p className="text-blue-100 text-xs mt-2">{missionData.message}</p>
              )}
              {missionData.rescueBaseName && (
                <div className="mt-2 pt-2 border-t border-white/20">
                  <p className="text-blue-100 text-xs font-medium">Dispatched from</p>
                  <p className="text-sm font-semibold">{missionData.rescueBaseName}</p>
                </div>
              )}
            </div>

            {/* Route Info Card */}
            <div className="card">
              <h3 className="font-bold text-gray-900 mb-4">Route Information</h3>
              
              <div className="space-y-3">
                <div className="flex items-start space-x-3">
                  <MapPin className="w-5 h-5 text-blue-600 flex-shrink-0 mt-0.5" />
                  <div>
                    <p className="text-sm font-medium text-gray-700">Total Distance</p>
                    <p className="text-2xl font-bold text-gray-900">
                      {formatDistance(missionData.totalDistance)}
                    </p>
                  </div>
                </div>

                <div className="flex items-start space-x-3">
                  <Route className="w-5 h-5 text-green-600 flex-shrink-0 mt-0.5" />
                  <div>
                    <p className="text-sm font-medium text-gray-700">Route Points</p>
                    <p className="text-2xl font-bold text-gray-900">
                      {missionData.path?.length || 0}
                    </p>
                  </div>
                </div>

                <div className="flex items-start space-x-3">
                  <AlertCircle className="w-5 h-5 text-purple-600 flex-shrink-0 mt-0.5" />
                  <div>
                    <p className="text-sm font-medium text-gray-700">Status</p>
                    <p className="text-lg font-bold text-gray-900">
                      {missionData.status}
                    </p>
                  </div>
                </div>
              </div>
            </div>

            {/* Cluster Info */}
            {missionData.cluster && (
              <ClusterCard cluster={missionData.cluster} />
            )}

            {/* Algorithm Info */}
            <div className="card bg-gray-50 border border-gray-200">
              <h3 className="font-bold text-gray-900 mb-2">Algorithm Details</h3>
              <ul className="text-sm text-gray-700 space-y-1">
                <li>• Dijkstra's shortest path algorithm</li>
                <li>• Max Heap priority queue</li>
                <li>• Dynamic edge blocking (flooded roads)</li>
                <li>• Geographic clustering (2km radius)</li>
                <li>• Multi-base fallback routing</li>
              </ul>
            </div>
          </div>

          {/* Middle & Right - Map */}
          <div className="lg:col-span-2">
            <div className="card p-0 overflow-hidden">
              <div className="p-4 bg-gradient-to-r from-green-500 to-blue-500 text-white">
                <div className="flex items-center justify-between">
                  <div>
                    <h3 className="font-bold text-lg">Rescue Route Map</h3>
                    <p className="text-sm text-green-50">
                      Green path shows the safest route avoiding flooded roads
                    </p>
                  </div>
                  <div className="bg-white/20 rounded-lg px-3 py-1">
                    <span className="text-sm font-semibold">Mission #{missionData.missionNumber}</span>
                  </div>
                </div>
              </div>
              <div className="h-[600px]">
                <MapView
                  route={missionData.path}
                  sosRequests={[]}
                  showGraph={true}
                  blockedRoads={blockedRoads}
                  rescueBases={rescueBases}
                  center={
                    missionData.cluster ? {
                      lat: missionData.cluster.center.latitude,
                      lng: missionData.cluster.center.longitude
                    } : undefined
                  }
                />
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Initial State - No Mission */}
      {!loadingHistory && missionHistory.length === 0 && !error && !loading && (
        <div className="card text-center py-16">
          <Route className="w-16 h-16 text-gray-300 mx-auto mb-4" />
          <h3 className="text-xl font-bold text-gray-900 mb-2">No Active Mission</h3>
          <p className="text-gray-600 mb-6">
            Click "Calculate Next Mission" to get the highest priority rescue route
          </p>
          <button
            onClick={handleGetNextMission}
            className="btn-primary mx-auto"
          >
            <Navigation className="w-5 h-5 inline mr-2" />
            Start Calculation
          </button>
        </div>
      )}

      {/* How It Works */}
      <div className="card bg-blue-50 border border-blue-200">
        <h3 className="font-bold text-blue-900 mb-3">How the Algorithm Works</h3>
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4 text-sm text-blue-800">
          <div>
            <div className="font-bold mb-1">1. Clustering</div>
            <p>Groups nearby SOS requests within 2km radius</p>
          </div>
          <div>
            <div className="font-bold mb-1">2. Prioritization</div>
            <p>Calculates priority using people count, severity, and distance</p>
          </div>
          <div>
            <div className="font-bold mb-1">3. Max Heap</div>
            <p>Selects highest priority cluster from queue</p>
          </div>
          <div>
            <div className="font-bold mb-1">4. Dijkstra</div>
            <p>Finds shortest safe path avoiding blocked roads</p>
          </div>
        </div>
      </div>
    </div>
  );
}