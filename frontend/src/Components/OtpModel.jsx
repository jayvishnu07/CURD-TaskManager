import React from 'react';

const OtpModel = ({ setShowModel, handleModel, title, description, buttonLabel, setEnteredOtp, setVerification }) => {
    return (
        <div className="confirmation_modal">
            <div className="confirmation_content">
                <h3>{title}</h3>
                <p>{description}</p>
                <input className='form_input_items_navbar' type="text" onChange={(e) => setEnteredOtp(e.target.value)} />
                <div className="confirmation_buttons">
                    <button className="delete_button" onClick={event => {
                        event.preventDefault();
                        event.stopPropagation();
                        handleModel(handleModel);
                    }}>{buttonLabel}</button>
                    <button className="cancel_button" onClick={() => { setShowModel(false); setVerification(false) }}>Cancel</button>
                </div>
            </div>
        </div>
    );
}

export default OtpModel