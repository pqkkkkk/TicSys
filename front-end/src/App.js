import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import SignIn from './pages/SignIn/SignIn';
import CustomerMainPage from './pages/Customer/CustomerMainPage';
import OrganizerMainPage from './pages/Organizer/OrganizerMainPage';
import SignUp from './pages/SignUp/SignUp';
import RegisterOrganizer from './pages/RegisterOrganizer/RegisterOrganizer';
import Error from './pages/Error/Error';
import EventManagement from './pages/Organizer/EventManagement/EventManagement';
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/*" Component={CustomerMainPage} />
        <Route path="/organizer/*" Component={OrganizerMainPage} />
        <Route path='/organizer/myevents/:eventId/*' element={<EventManagement />}/>
        <Route path="/signin" Component={SignIn} />
        <Route path="/signup" Component={SignUp} />
        <Route path="/become-organizer" Component={RegisterOrganizer} />
        <Route path="/error" Component={Error} />
      </Routes>
    </Router>
  );
}

export default App;
