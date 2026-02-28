import { BarChart3, ShieldAlert, Waves } from 'lucide-react';
import { useEffect, useState } from 'react';
import MapView from '../components/MapView';
import PriorityList from '../components/PriorityList';
import RoadBlockForm from '../components/RoadBlockForm';
import { getBlockedRoads, getGraphStats, getPendingSosRequests } from '../services/api';

export default function AdminDashboard() {
  const [sosRequests, setSosRequests] = useState([]);
  const [graphStats, setGraphStats] = useState(null);
  const [blockedRoads, setBlockedRoads] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const [sosData, statsData, blockedData] = await Promise.all([
        getPendingSosRequests(),
        getGraphStats(),
        getBlockedRoads()
      ]);
      
      setSosRequests(sosData.requests || []);
      setGraphStats(statsData.statistics);
      setBlockedRoads(blockedData.blockedRoads || []);
    } catch (error) {
      console.error('Failed to load dashboard data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleRoadBlocked = () => {
    loadData(); // Refresh all data including blocked roads from DB
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-96">
        <div className="text-center">
          <div className="spinner mx-auto mb-4" />
          <p className="text-gray-600">Loading dashboard...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header Stats */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div className="card bg-gradient-to-br from-blue-500 to-blue-600 text-white">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-blue-100 text-sm font-medium">Pending SOS</p>
              <p className="text-3xl font-bold mt-1">{sosRequests.length}</p>
            </div>
            <ShieldAlert className="w-12 h-12 text-blue-200" />
          </div>
        </div>

        <div className="card bg-gradient-to-br from-purple-500 to-purple-600 text-white">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-purple-100 text-sm font-medium">Total Affected</p>
              <p className="text-3xl font-bold mt-1">
                {sosRequests.reduce((sum, req) => sum + req.affectedPeople, 0)}
              </p>
            </div>
            <BarChart3 className="w-12 h-12 text-purple-200" />
          </div>
        </div>

        <div className="card bg-gradient-to-br from-red-500 to-red-600 text-white">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-red-100 text-sm font-medium">Blocked Roads</p>
              <p className="text-3xl font-bold mt-1">{blockedRoads.length}</p>
            </div>
            <Waves className="w-12 h-12 text-red-200" />
          </div>
        </div>
      </div>

      {/* Graph Statistics */}
      {graphStats && (
        <div className="card bg-gray-50 border border-gray-200">
          <h3 className="font-bold text-gray-900 mb-2">Road Network Status</h3>
          <p className="text-gray-700">{graphStats}</p>
        </div>
      )}

      {/* Main Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Left Column - Priority List */}
        <div className="lg:col-span-1">
          <PriorityList sosRequests={sosRequests} />
        </div>

        {/* Middle Column - Map */}
        <div className="lg:col-span-1">
          <div className="card p-0 overflow-hidden">
            <div className="p-4 bg-gray-50 border-b">
              <h3 className="font-bold text-gray-900">Live Map View</h3>
              <p className="text-sm text-gray-600">All SOS requests and road network</p>
            </div>
            <div className="h-[500px]">
              <MapView
                sosRequests={sosRequests}
                blockedRoads={blockedRoads}
                showGraph={true}
              />
            </div>
          </div>
        </div>

        {/* Right Column - Road Block Form */}
        <div className="lg:col-span-1">
          <RoadBlockForm onRoadBlocked={handleRoadBlocked} blockedRoads={blockedRoads} />
        </div>
      </div>
    </div>
  );
}