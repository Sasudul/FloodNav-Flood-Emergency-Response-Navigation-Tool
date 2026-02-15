import { Route, Routes } from 'react-router-dom';
import Navigation from './components/Navigation';
import AdminDashboard from './pages/AdminDashboard';
import RouteView from './pages/RouteView';
import VictimSOS from './pages/VictimSOS';

function App() {
  return (
    <div className="min-h-screen bg-slate-50 font-sans text-slate-900">
      <Navigation />
      <main className="animate-fade-in">
        <Routes>
          <Route path="/" element={<VictimSOS />} />
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/rescue" element={<RouteView />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
