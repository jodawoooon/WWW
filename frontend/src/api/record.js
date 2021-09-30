import { requestPost } from "@/api/request.js";
import SERVER from "./drf.js";

export const recordApi = {
  getWalkData: (data, headers) => {
    let check = true;
    let url = SERVER.URL + SERVER.ROUTES.users + `/${data.type}`;
    if (data.userId) {
      if (check) {
        url += `?`;
        check = false;
      } else {
        url += `&`;
      }
      url += `userId=${data.userId}`;
    }
    if (data.returnType) {
      if (check) {
        url += `?`;
        check = false;
      } else {
        url += `&`;
      }
      url += `returnType=${data.returnType}`;
    }
    // return requestGet(url, headers);
    // return requestGet(SERVER.URL + SERVER.ROUTES.users + `/${data.type}` +`?userId=${data.userId}`, headers);
  },
};
export default recordApi;
