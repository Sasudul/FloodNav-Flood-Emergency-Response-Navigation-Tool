import { AlertTriangle, TrendingUp, Users } from 'lucide-react';
import { getSeverityColor } from '../utils/mapUtils';

export default function PriorityList({ sosRequests }) {
  // Sort by priority score (descending)
  const sortedRequests = [...sosRequests].sort((a, b) => b.priorityScore - a.priorityScore);

  return (
    <div className="card">
      <div className="flex items-center justify-between mb-4">
        <h3 className="font-bold text-gray-900">Priority Queue</h3>
        <span className="text-sm text-gray-600">{sortedRequests.length} requests</span>
      </div>

      {sortedRequests.length === 0 ? (
        <div className="text-center py-8 text-gray-500">
          <AlertTriangle className="w-12 h-12 mx-auto mb-2 text-gray-300" />
          <p>No pending SOS requests</p>
        </div>
      ) : (
        <div className="space-y-3 max-h-[560px] overflow-y-auto">
          {sortedRequests.map((request, index) => (
            <div
              key={request.id}
              className="p-3 bg-gray-50 rounded-lg border border-gray-200 hover:shadow-md transition-shadow"
            >
              <div className="flex items-start justify-between mb-2">
                <div className="flex items-center space-x-2">
                  <span className="flex items-center justify-center w-6 h-6 bg-blue-600 text-white text-xs font-bold rounded-full">
                    {index + 1}
                  </span>
                  <span className="font-semibold text-gray-900">Request #{request.id}</span>
                </div>
                <div className="flex items-center space-x-1 text-xs">
                  <TrendingUp className="w-3 h-3 text-purple-600" />
                  <span className="font-bold text-purple-900">{request.priorityScore}</span>
                </div>
              </div>

              <div className="grid grid-cols-2 gap-2 text-sm">
                <div className="flex items-center space-x-1">
                  <Users className="w-3 h-3 text-gray-600" />
                  <span className="text-gray-700">{request.affectedPeople} people</span>
                </div>
                <div className="flex items-center space-x-1">
                  <AlertTriangle className="w-3 h-3" style={{ color: getSeverityColor(request.severityLevel) }} />
                  <span
                    className="px-2 py-0.5 rounded text-xs font-medium text-white"
                    style={{ backgroundColor: getSeverityColor(request.severityLevel) }}
                  >
                    {request.severityLevel}
                  </span>
                </div>
              </div>

              {request.notes && (
                <p className="text-xs text-gray-600 mt-2 line-clamp-2">{request.notes}</p>
              )}

              <div className="text-xs text-gray-500 mt-2">
                📍 {request.latitude.toFixed(4)}°, {request.longitude.toFixed(4)}°
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}