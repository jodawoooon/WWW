import { requestGet } from "@/api/request.js";
import SERVER from "./drf.js";

export const mainApi = {
  getRankData: (data,headers) => {
    let url = SERVER.URL + SERVER.ROUTES.main + `/${data.type}`;
    return requestGet(url,headers);
    // return requestGet(SERVER.URL + SERVER.ROUTES.users + `/${data.type}` +`?userId=${data.userId}`, headers);
  },
  getTodayWalk: (data,headers)=>{
    let url = SERVER.URL+SERVER.ROUTES.main+`/${data.type}`+`/test`+`/${data.date}`;
    return requestGet(url,headers);
  },
};
export default mainApi;
