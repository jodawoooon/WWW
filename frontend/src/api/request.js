import axios from "axios";

export const requestGet = async (url, headers) => {
  try {
    console.log(url);
    const response = await axios.get(url, { headers });
    if (response.status === 200) {
      return response.data;
    }
    throw new Error();
  } catch (e) {
    throw new Error(e);
  }
};

export const requestPost = async (url, data, headers) => {
  try {
    const response = await axios.post(url, data, { headers });
    if (response.status === 200 || response.status === 201) {
      return response.data;
    }
    throw new Error();
  } catch (e) {
    throw new Error(e);
  }
};

export const requestPut = async (url, data, headers) => {
  try {
    const response = await axios.put(url, data, { headers });
    if (response.status === 200) {
      return response.data;
    }
    throw new Error();
  } catch (e) {
    throw new Error(e);
  }
};

export const requestDelete = async (url, headers) => {
  try {
    const response = await axios.delete(url, { headers });
    if (response.status === 200) {
      return response.data;
    }
    throw new Error();
  } catch (e) {
    throw new Error(e);
  }
};
