import  { requestGet} from '@/api/request.js';
import SERVER from './drf.js';


export const myCourseApi = {

    getCourseData: (data, headers) => {
        let check = true;
        let url = SERVER.URL + SERVER.ROUTES.myCourse + `/${data.type}`;
        if (data.userId) {
            if (check) {
                url += `?`;
                check = false;
            }
            else {
                url += `&`;
            }
            url += `userId=${data.userId}`;
        }
        return requestGet(url, headers);
    },

}
export default myCourseApi;