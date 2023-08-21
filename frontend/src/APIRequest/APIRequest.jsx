import axios from 'axios';

//Custom components
import { keycloak } from '../App';
import ShowToast from '../Components/ShowToast';


export const makeGetRequest = (url, setTask, setCount) => {
  axios({
    url,
    method: 'GET',
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
    },
  })
    .then((res) => { setTask(res?.data?.data); setCount(res?.data?.count); })
    .catch((err) => {
      ShowToast({ message: `${err?.response?.data?.message}`, type: 'error' });
    })
}


export const makeDeleteRequest = (url, setRecentEditHappen, setShowDeleteModel) => {
  axios({
    url,
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
    },
  })
    .then((res) => { setRecentEditHappen((prev) => !prev); setShowDeleteModel(false); ShowToast({ message: res?.data?.message, type: 'success' }); })
    .catch((err) => {
      ShowToast({ message: `${err?.response?.data?.message}`, type: 'error' });
    })
}

export const makePutAndPostRequest = (method, url, data, callbacks) => {
  axios({
    url,
    method,
    data,
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
    },
  })
    .then((res) => {
      if (callbacks.onSuccess && typeof callbacks.onSuccess === 'function') {
        callbacks.onSuccess(res);
        ShowToast({ message: res?.data?.message, type: 'success' });
      }
    })
    .catch((err) => {
      ShowToast({ message: `${err?.response?.data?.message}`, type: 'error' });
    })
}






