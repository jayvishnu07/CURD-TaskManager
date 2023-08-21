import React from 'react';
import ReactDOM from 'react-dom/client';

//libraries
import axios from 'axios';
import { BrowserRouter as Router } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';

//style sheets
import 'react-toastify/dist/ReactToastify.css';
import './index.css';

//custom components
import App from './App';
import TaskContextProvider from './ContextApi/ContextApi';

//axios base URL
axios.defaults.baseURL = `https://localhost:8444/api`



const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <Router>
        <TaskContextProvider>
            <App />
        </TaskContextProvider>
        <ToastContainer
            position="top-right"
            autoClose={true}
            hideProgressBar={true}
            rtl={false}
            draggable
            theme="colored"
        />
    </Router>

);




// https://github.com/tp02ga/AssignmentSubmissionApp

// Coders Campus

// https://youtu.be/89l2o70GdO8


// "start": "HTTPS=true SSL_CRT_FILE=./ssl/localhost.pem SSL_KEY_FILE=./ssl/localhost-key.pem react-scripts start",


// https://www.tutorialworks.com/java-trust-ssl/

// https://youtu.be/Y7au8oWCFMc

// https://www.codejava.net/frameworks/spring-boot/configure-https-with-self-signed-certificate

// Laith Academy


// Learning Software



// https://github.com/czetsuya/Spring-Keycloak-with-REST-API

// https://github.com/czetsuya/keycloak-react

// https://github.com/hellolambdacode/Secure-Your-Spring-Boot-Microservices-with-Keycloak-using-OpenID-and-OAuth-2.0










// https://medium.com/codeshakeio/configure-keycloak-to-use-google-as-an-idp-4e3c59583345

// client_id : 271666586285-m47d85n879ssb6oltted5d62llodjmu5.apps.googleusercontent.com

// client_secret : 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


// springboot + keycloak + reactjs 

// https://github.com/ivangfr/springboot-react-keycloak

// https://medium.com/@nishada/securing-a-react-app-using-keycloak-ac0ee5dd4bfc




// https://localhost:3000/?state=hub34b3u4bl2j3bnlj&session_state=2267d2ea-9e92-4863-a59f-bbf3d427bbd5&code=7ee25570-8c9b-4506-94a9-28afd176250b.2267d2ea-9e92-4863-a59f-bbf3d427bbd5.cd764f22-0933-4122-8290-2474edeb4b25