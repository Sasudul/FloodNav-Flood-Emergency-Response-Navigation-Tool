import { MapPin, Route as RouteIcon, ShieldAlert, Waves } from 'lucide-react';
import { Link, Route, BrowserRouter as Router, Routes, useLocation } from 'react-router-dom';
import AdminDashboard from './pages/AdminDashboard';
import RouteView from './pages/RouteView';
import VictimSOS from './pages/VictimSOS';

function Navigation() {
  const location = useLocation();
  
  const navItems = [
    { path: '/', label: 'SOS Request', icon: MapPin },
    { path: '/admin', label: 'Admin Dashboard', icon: ShieldAlert },
    { path: '/rescue', label: 'Rescue Routes', icon: RouteIcon }
  ];
  
  return (
    <nav className="bg-white shadow-md">
      <div className="max-w-7xl mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center space-x-2">
            <Waves className="w-8 h-8 text-blue-600" />
            <span className="text-xl font-bold text-gray-900">FloodNav</span>
            <span className="text-sm text-gray-500 ml-2">Cyclone Ditwah Response</span>
          </div>
          
          <div className="flex space-x-1">
            {navItems.map(({ path, label, icon: Icon }) => (
              <Link
                key={path}
                to={path}
                className={`flex items-center space-x-2 px-4 py-2 rounded-lg transition-colors ${
                  location.pathname === path
                    ? 'bg-blue-600 text-white'
                    : 'text-gray-700 hover:bg-gray-100'
                }`}
              >
                <Icon className="w-4 h-4" />
                <span className="font-medium">{label}</span>
              </Link>
            ))}
          </div>
        </div>
      </div>
    </nav>
  );
}

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Navigation />
        
        <main className="max-w-7xl mx-auto px-4 py-6">
          <Routes>
            <Route path="/" element={<VictimSOS />} />
            <Route path="/admin" element={<AdminDashboard />} />
            <Route path="/rescue" element={<RouteView />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;