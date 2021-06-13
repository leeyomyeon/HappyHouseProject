import { createInstance } from './index.js';

const instance = createInstance();
// const config = {
//   headers: { "access-token": localStorage.getItem("access-token") }
// };

function login(user, success, fail) {
  instance.defaults.headers['access-token'] = window.localStorage.getItem('access-token');
  const body = {
    id: user.userid,
    pass: user.password,
  };

  instance.post('/login/confirm', JSON.stringify(body)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  instance.defaults.headers['access-token'] = window.localStorage.getItem('access-token');
  await instance.get(`/login/info/${userid}`).then(success).catch(fail);
}

export { login, findById };
