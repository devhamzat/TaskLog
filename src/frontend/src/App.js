import CreateAccount from "./pages/CreateAccount";
import Landing from "./pages/Landing";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route exact path="/" element={<Landing />} />
          <Route exact path="/createAccount" element={<CreateAccount />} />
          <Route exact path="/signIn" element={<Login />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
