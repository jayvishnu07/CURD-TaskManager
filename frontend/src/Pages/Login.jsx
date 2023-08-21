import React, { useState } from 'react';

//image source from assests
import login_image from '../assets/login_image.webp';
//style sheets
import '../Css/Pages.style/Login.css';

//custom components

//react icons
import { keycloak } from '../App';



function Login() {

  //state to disable enter button
  const [isLoginButtonDisabled, setIsLoginButtonDisabled] = useState(true)



  return (
    <div className="App">
      <div className="image">
        <img src={login_image} alt="" />
        <button onClick={() => keycloak.login()} className='get-button' >Get started</button>
      </div>

    </div>
  );
}

export default Login;



