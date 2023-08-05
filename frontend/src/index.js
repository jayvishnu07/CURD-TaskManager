import React from 'react';
import ReactDOM from 'react-dom/client';

//libraries
import { BrowserRouter as Router } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import axios from 'axios';

//style sheets
import './index.css';
import 'react-toastify/dist/ReactToastify.css';

//custom components
import App from './App';
import TaskContextProvider from './ContextApi/ContextApi';

//axios base URL
axios.defaults.baseURL = `https://localhost:8443/api`

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Router>
        <TaskContextProvider>
            <App />
        </TaskContextProvider>
        <ToastContainer
            position="top-right"
            autoClose={true}
            hideProgressBar={false}
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