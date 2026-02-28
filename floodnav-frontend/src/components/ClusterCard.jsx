import { MapPin, Target, TrendingUp, Users } from 'lucide-react';
import { formatDistance } from '../utils/mapUtils';

export default function ClusterCard({ cluster }) {
  return (
    <div className="card bg-gradient-to-br from-purple-50 to-blue-50 border border-purple-200">
      <div className="flex items-center space-x-2 mb-4">
        <Target className="w-6 h-6 text-purple-600" />
        <h3 className="font-bold text-gray-900">Priority Cluster #{cluster.clusterId}</h3>
      </div>

      <div className="space-y-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-2 text-gray-700">
            <Users className="w-4 h-4" />
            <span className="text-sm">Total People</span>
          </div>
          <span className="font-bold text-lg text-purple-900">{cluster.totalPeople}</span>
        </div>

        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-2 text-gray-700">
            <TrendingUp className="w-4 h-4" />
            <span className="text-sm">Priority Score</span>
          </div>
          <span className="font-bold text-lg text-purple-900">
            {cluster.priorityScore.toFixed(2)}
          </span>
        </div>

        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-2 text-gray-700">
            <MapPin className="w-4 h-4" />
            <span className="text-sm">Distance from Base</span>
          </div>
          <span className="font-bold text-lg text-purple-900">
            {formatDistance(cluster.distanceFromBase)}
          </span>
        </div>

        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-2 text-gray-700">
            <Target className="w-4 h-4" />
            <span className="text-sm">SOS Requests</span>
          </div>
          <span className="font-bold text-lg text-purple-900">{cluster.requestCount}</span>
        </div>
      </div>

      <div className="mt-4 pt-4 border-t border-purple-200">
        <p className="text-xs text-purple-800">
          <span className="font-semibold">Center Location:</span>{' '}
          {cluster.center.latitude.toFixed(4)}°, {cluster.center.longitude.toFixed(4)}°
        </p>
      </div>
    </div>
  );
}