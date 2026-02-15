
const RouteView = () => {
  return (
    <div className="p-6 space-y-6">
      <h1 className="text-3xl font-bold">Rescue Route Calculator</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {/* Sidebar / Controls */}
        <div className="space-y-6">
          {/* Action Button */}
          <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50">
            <button className="w-full py-2 px-4 bg-blue-600 text-white rounded hover:bg-blue-700">
              Calculate Route
            </button>
          </div>

          {/* Error/Info Messages */}
          <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50">
            <h2 className="text-lg font-semibold mb-2">Status</h2>
            <div className="h-16 bg-gray-200 flex items-center justify-center text-gray-500">
              [Error or Info Messages Placeholder]
            </div>
          </div>

          {/* Mission Data Block */}
          <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50">
            <h2 className="text-lg font-semibold mb-2">Mission Data</h2>
            <div className="h-32 bg-gray-200 flex items-center justify-center text-gray-500">
              [Mission Stats & Details Placeholder]
            </div>
          </div>

          {/* How It Works */}
          <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50">
            <h2 className="text-lg font-semibold mb-2">How It Works</h2>
            <ol className="list-decimal list-inside text-gray-600 space-y-2">
              <li>Select start point</li>
              <li>Select destination</li>
              <li>Avoid flooded areas</li>
              <li>Start navigation</li>
            </ol>
          </div>
        </div>

        {/* Map Area */}
        <div className="md:col-span-2">
          <div className="p-4 border border-dashed border-gray-400 rounded bg-gray-50 h-[600px]">
            <h2 className="text-xl font-semibold mb-2">Navigation Map</h2>
            <div className="h-full bg-gray-200 flex items-center justify-center text-gray-500">
              [Interactive Map Placeholder]
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RouteView;
