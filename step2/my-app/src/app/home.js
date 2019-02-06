import './home.css';

const template = require("./home.html");

import controller from "./home.controller";

const component={
  template,
  controller,
  controllerAs: 'ctrl'
}

const HomeComponent = {
  name: 'home',
  component
};

export default HomeComponent;
