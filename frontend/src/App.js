import jwt_decode from 'jwt-decode';
import Keycloak from 'keycloak-js';
import { React } from 'react';
import './App.css';
import Body from './Components/Body.jsx';
import Navbar from './Components/Navbar.jsx';
import { ContextState } from './ContextApi/ContextApi';
import Login from './Pages/Login';

export const keycloak = new Keycloak({
  url: 'https://localhost:8443',
  realm: 'oauth_demo',
  clientId: 'client_id',
  scope: "openid profile email"
});


try {
  const authenticated = await keycloak.init({ onLoad: 'login-required', checkLoginIframe: false });
  console.log(`User is ${authenticated ? 'authenticated' : 'not authenticated'}`);
  console.log({ keycloak });
} catch (error) {
  console.error('Failed to initialize adapter:', error);
}


const App = () => {
  const { setUserName } = ContextState();
  const { userName } = ContextState();

  const getUserName = (jwtToken) => {
    try {
      const decodedToken = jwt_decode(jwtToken);
      const user = decodedToken.given_name ? decodedToken.given_name : decodedToken.preferred_username;
      console.log('user name here => ', user);
      setUserName(user);
      localStorage.setItem('userName', user);
    } catch (error) {
      console.error('Error decoding JWT:', error);
    }
  };

  getUserName(keycloak.token)

  return (
    <div className="App">
      {keycloak.authenticated ? (
        <div className="main_body">
          <Navbar />
          <Body />
        </div>
      ) : (
        <>
          <Login />
        </>

      )}
    </div >
  );
};

export default App;


