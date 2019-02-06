import './comment.css';

const template = require("./comment.html");

import controller from "./comment.controller";

const component={
  template,
  controller,
  controllerAs: 'ctrl',
}

const LoginComponent = {
  name: 'comment',
  component
};

export default LoginComponent;
