import React from 'react';
import ScaleLoader from "react-spinners/ScaleLoader";
import OtpModel from '../Components/OtpModel';
import '../Css/Pages.style/Verification.css';

const Verification = ({ showOtpModel, setEnteredOtp, setShowOtpModel, verifyOTP, setVerification }) => {
  return (
    <div className='verify_wrapper' >
      {
        showOtpModel
          ?
          <OtpModel title={"Check mail for OTP"} description={"OTP will be expired in 2 minutes."} setEnteredOtp={setEnteredOtp} setVerification={setVerification} buttonLabel={"Verify"} setShowModel={setShowOtpModel} handleModel={verifyOTP}></OtpModel>
          :
          <div className="content">
            <ScaleLoader />
          </div>
      }
    </div>
  )
}

export default Verification