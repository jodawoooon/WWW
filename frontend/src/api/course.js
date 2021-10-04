import { requestGet } from "@/api/request.js";
import SERVER from "./drf.js";

export const myCourseApi = {
  getCourseData: (data, headers) => {
    let check = true;
    let url = SERVER.URL + SERVER.ROUTES.course + `/${data.type}`;
    if (data.userId) {
      if (check) {
        url += `?`;
        check = false;
      } else {
        url += `&`;
      }
      url += `userId=${data.userId}`;
    }
    if (data.courseId) {
      if (check) {
        url += `?`;
        check = false;
      } else {
        url += `&`;
      }
      url += `courseId=${data.courseId}`;
    }
    return requestGet(url, headers);
  },
};
export default myCourseApi;
