import { CheckCircle, Waves, XCircle } from 'lucide-react';
import { useMemo, useState } from 'react';
import { blockRoadRange, unblockRoadRange } from '../services/api';
import { GRAPH_NODES } from '../utils/mapUtils';

/**
 * Parse the numeric part from a node ID like "KEL135" -> 135
 */
const parseNodeNumber = (nodeId) => {
  if (!nodeId || !nodeId.toUpperCase().startsWith('KEL')) return -1;
  return parseInt(nodeId.substring(3), 10) || -1;
};

/**
 * Calculate how many edges will be affected by a range
 */
const getRangeEdgeCount = (startNode, endNode) => {
  const startNum = parseNodeNumber(startNode);
  const endNum = parseNodeNumber(endNode);
  if (startNum === -1 || endNum === -1) return 0;
  return Math.abs(endNum - startNum);
};

/**
 * Generate edge labels for a range
 */
const getRangeEdges = (startNode, endNode) => {
  const startNum = parseNodeNumber(startNode);
  const endNum = parseNodeNumber(endNode);
  if (startNum === -1 || endNum === -1) return [];
  const from = Math.min(startNum, endNum);
  const to = Math.max(startNum, endNum);
  const edges = [];
  for (let i = from; i < to; i++) {
    const a = `KEL${String(i).padStart(2, '0')}`;
    const b = `KEL${String(i + 1).padStart(2, '0')}`;
    edges.push(`${a} ↔ ${b}`);
  }
  return edges;
};

/**
 * Group blocked roads into contiguous ranges.
 * e.g., [KEL135↔KEL136, KEL136↔KEL137, KEL137↔KEL138, KEL138↔KEL139, KEL01↔KEL02]
 *   => [{start: "KEL01", end: "KEL02", count: 1}, {start: "KEL135", end: "KEL139", count: 4}]
 */
const groupBlockedRoads = (blockedRoads) => {
  if (!blockedRoads || blockedRoads.length === 0) return [];

  // Collect all blocked node numbers as a sorted set of edge pairs
  const blockedNums = new Set();
  for (const road of blockedRoads) {
    const startNum = parseNodeNumber(road.startNode);
    const endNum = parseNodeNumber(road.endNode);
    if (startNum !== -1 && endNum !== -1) {
      blockedNums.add(Math.min(startNum, endNum));
    }
  }

  // Sort the edge start numbers
  const sorted = [...blockedNums].sort((a, b) => a - b);
  if (sorted.length === 0) return [];

  // Group contiguous numbers into ranges
  const ranges = [];
  let rangeStart = sorted[0];
  let rangePrev = sorted[0];

  for (let i = 1; i < sorted.length; i++) {
    if (sorted[i] === rangePrev + 1) {
      // Contiguous — extend the range
      rangePrev = sorted[i];
    } else {
      // Gap — close current range and start new one
      ranges.push({
        start: `KEL${String(rangeStart).padStart(2, '0')}`,
        end: `KEL${String(rangePrev + 1).padStart(2, '0')}`,
        count: rangePrev - rangeStart + 1
      });
      rangeStart = sorted[i];
      rangePrev = sorted[i];
    }
  }
  // Close the last range
  ranges.push({
    start: `KEL${String(rangeStart).padStart(2, '0')}`,
    end: `KEL${String(rangePrev + 1).padStart(2, '0')}`,
    count: rangePrev - rangeStart + 1
  });

  return ranges;
};

export default function RoadBlockForm({ onRoadBlocked, blockedRoads = [] }) {
  const [formData, setFormData] = useState({
    startNode: '',
    endNode: '',
    reason: ''
  });
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState(null);
  const [mode, setMode] = useState('block'); // 'block' or 'unblock'

  // Group blocked roads into contiguous ranges for the unblock dropdown
  const blockedRanges = useMemo(() => groupBlockedRoads(blockedRoads), [blockedRoads]);

  const handleRangeSelect = (value) => {
    if (value) {
      const [start, end] = value.split('|');
      setFormData(prev => ({ ...prev, startNode: start, endNode: end }));
    } else {
      setFormData(prev => ({ ...prev, startNode: '', endNode: '' }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setResponse(null);

    try {
      const apiCall = mode === 'block' ? blockRoadRange : unblockRoadRange;
      const result = await apiCall(formData);
      
      setResponse({ type: 'success', data: result });
      
      if (onRoadBlocked) {
        onRoadBlocked();
      }

      // Reset form
      setTimeout(() => {
        setFormData({ startNode: '', endNode: '', reason: '' });
        setResponse(null);
      }, 3000);
    } catch (error) {
      setResponse({
        type: 'error',
        message: error.response?.data?.message || `Failed to ${mode} road(s)`
      });
    } finally {
      setLoading(false);
    }
  };

  const edgeCount = getRangeEdgeCount(formData.startNode, formData.endNode);
  const previewEdges = getRangeEdges(formData.startNode, formData.endNode);

  return (
    <div className="card">
      <div className="flex items-center space-x-3 mb-4">
        <Waves className="w-6 h-6 text-red-600" />
        <h3 className="font-bold text-gray-900">Road Management</h3>
      </div>

      {/* Mode Toggle */}
      <div className="flex space-x-2 mb-4">
        <button
          onClick={() => { setMode('block'); setFormData({ startNode: '', endNode: '', reason: '' }); }}
          className={`flex-1 py-2 px-4 rounded-lg font-medium transition-colors ${
            mode === 'block'
              ? 'bg-red-600 text-white'
              : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
          }`}
        >
          <XCircle className="w-4 h-4 inline mr-1" />
          Block Roads
        </button>
        <button
          onClick={() => { setMode('unblock'); setFormData({ startNode: '', endNode: '', reason: '' }); }}
          className={`flex-1 py-2 px-4 rounded-lg font-medium transition-colors ${
            mode === 'unblock'
              ? 'bg-green-600 text-white'
              : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
          }`}
        >
          <CheckCircle className="w-4 h-4 inline mr-1" />
          Unblock Roads
        </button>
      </div>

      <form onSubmit={handleSubmit} className="space-y-4">
        {mode === 'block' ? (
          <>
            {/* From Node */}
            <div>
              <label className="label">From Node</label>
              <select
                value={formData.startNode}
                onChange={(e) => setFormData(prev => ({ ...prev, startNode: e.target.value }))}
                className="input-field"
                required
              >
                <option value="">Select start node...</option>
                {GRAPH_NODES.map(node => (
                  <option key={node.id} value={node.id}>
                    {node.id} - {node.name}
                  </option>
                ))}
              </select>
            </div>

            {/* To Node */}
            <div>
              <label className="label">To Node</label>
              <select
                value={formData.endNode}
                onChange={(e) => setFormData(prev => ({ ...prev, endNode: e.target.value }))}
                className="input-field"
                required
              >
                <option value="">Select end node...</option>
                {GRAPH_NODES.map(node => (
                  <option key={node.id} value={node.id}>
                    {node.id} - {node.name}
                  </option>
                ))}
              </select>
            </div>

            {/* Range Preview */}
            {edgeCount > 0 && (
              <div className="p-3 bg-red-50 border border-red-200 rounded-lg">
                <p className="text-sm font-semibold text-red-800 mb-1">
                  ⚠️ {edgeCount} road segment{edgeCount > 1 ? 's' : ''} will be blocked:
                </p>
                <div className="max-h-24 overflow-y-auto">
                  {previewEdges.map((edge, i) => (
                    <p key={i} className="text-xs text-red-700">{edge}</p>
                  ))}
                </div>
              </div>
            )}

            {/* Reason */}
            <div>
              <label className="label">Reason</label>
              <input
                type="text"
                value={formData.reason}
                onChange={(e) => setFormData(prev => ({ ...prev, reason: e.target.value }))}
                placeholder="e.g., Severe flooding - 2m water level"
                className="input-field"
              />
            </div>
          </>
        ) : (
          <>
            {/* Unblock mode — single dropdown showing blocked road ranges */}
            <div>
              <label className="label">Select Blocked Road</label>
              {blockedRanges.length === 0 ? (
                <div className="p-3 bg-gray-50 border border-gray-200 rounded-lg text-sm text-gray-500 text-center">
                  No roads are currently blocked
                </div>
              ) : (
                <select
                  value={formData.startNode && formData.endNode ? `${formData.startNode}|${formData.endNode}` : ''}
                  onChange={(e) => handleRangeSelect(e.target.value)}
                  className="input-field"
                  required
                >
                  <option value="">Select a blocked road...</option>
                  {blockedRanges.map((range, index) => (
                    <option key={index} value={`${range.start}|${range.end}`}>
                      {range.start} – {range.end}  ({range.count} segment{range.count > 1 ? 's' : ''})
                    </option>
                  ))}
                </select>
              )}
            </div>

            {/* Unblock Preview */}
            {edgeCount > 0 && (
              <div className="p-3 bg-green-50 border border-green-200 rounded-lg">
                <p className="text-sm font-semibold text-green-800 mb-1">
                  ✅ {edgeCount} road segment{edgeCount > 1 ? 's' : ''} will be unblocked:
                </p>
                <div className="max-h-24 overflow-y-auto">
                  {previewEdges.map((edge, i) => (
                    <p key={i} className="text-xs text-green-700">{edge}</p>
                  ))}
                </div>
              </div>
            )}
          </>
        )}

        {/* Submit Button */}
        <button
          type="submit"
          disabled={loading || edgeCount === 0}
          className={`w-full flex items-center justify-center space-x-2 ${
            mode === 'block' ? 'btn-danger' : 'btn-success'
          }`}
        >
          {loading ? (
            <>
              <div className="spinner w-5 h-5 border-2" />
              <span>Processing...</span>
            </>
          ) : (
            <>
              {mode === 'block' ? (
                <>
                  <XCircle className="w-5 h-5" />
                  <span>Block {edgeCount > 0 ? `${edgeCount} Road${edgeCount > 1 ? 's' : ''}` : 'Roads'}</span>
                </>
              ) : (
                <>
                  <CheckCircle className="w-5 h-5" />
                  <span>Unblock {edgeCount > 0 ? `${edgeCount} Road${edgeCount > 1 ? 's' : ''}` : 'Roads'}</span>
                </>
              )}
            </>
          )}
        </button>
      </form>

      {/* Response Message */}
      {response && (
        <div className={`mt-4 p-3 rounded-lg ${
          response.type === 'success'
            ? 'bg-green-50 text-green-800 border border-green-200'
            : 'bg-red-50 text-red-800 border border-red-200'
        }`}>
          <p className="text-sm font-medium">
            {response.type === 'success' ? response.data.message : response.message}
          </p>
        </div>
      )}

      {/* Info Box */}
      <div className="mt-4 p-3 bg-yellow-50 border border-yellow-200 rounded-lg">
        <p className="text-xs text-yellow-800">
          <strong>Note:</strong> Select a range of nodes to block/unblock all roads between them.
          For example, selecting KEL135 to KEL139 will block 4 road segments.
          Blocked roads are automatically avoided by the routing algorithm.
        </p>
      </div>
    </div>
  );
}