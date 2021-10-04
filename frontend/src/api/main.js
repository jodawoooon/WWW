import { requestGet } from "@/api/request.js";
import SERVER from "./drf.js";

export const mainApi = {
  getRecommendData: (data, headers) => {
    let url =
      SERVER.URL + SERVER.ROUTES.main + `/${data.type}` + `/${data.sigu}`;
    return requestGet(url, headers);
  },
  getRankData: (data, headers) => {
    let url = SERVER.URL + SERVER.ROUTES.main + `/${data.type}`;
    return requestGet(url, headers);
  },
  getTodayWalk: (data, headers) => {
    let url =
      SERVER.URL +
      SERVER.ROUTES.main +
      `/${data.type}` +
      `/${data.userName}` +
      `/${data.date}`;
    return requestGet(url, headers);
  },
};
export default mainApi;
