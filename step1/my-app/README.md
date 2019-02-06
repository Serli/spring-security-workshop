# angularjs-webpack

[![Dependency Status](https://david-dm.org/preboot/angularjs-webpack/status.svg)](https://david-dm.org/preboot/angular-webpack#info=dependencies) [![devDependency Status](https://david-dm.org/preboot/angularjs-webpack/dev-status.svg)](https://david-dm.org/preboot/angularjs-webpack#info=devDependencies)

A complete, yet simple, starter for AngularJS using Webpack.

This workflow serves as a starting point for building AngularJS (1.x) applications using Webpack 2.x. Should be noted that apart from the pre-installed angular package, this workflow is pretty much generic.

* Heavily commented webpack configuration with reasonable defaults.
* ES6, and ES7 support with babel.
* Source maps included in all builds.
* Development server with live reload.
* Production builds with cache busting.
* Testing environment using karma to run tests and jasmine as the framework.
* Code coverage when tests are run.
* No gulp and no grunt, just npm scripts.

>Warning: Make sure you're using the latest version of Node.js and NPM

### Quick start

> Clone/Download the repo then edit `login.jsjs` inside [`/src/login/loginin.js`](/src/login/login.js)

```bash
# clone our repo
$ git clone https://github.com/preboot/angularjs-webpack.git my-login

# change directory to your login
$ cd my-login

# install the dependencies with npm
$ npm install

# start the server
$ npm start
```

go to [http://localhost:8080](http://localhost:8080) in your browser.

# Table of Contents

* [Getting Started](#getting-started)
    * [Dependencies](#dependencies)
    * [Installing](#installing)
    * [Running the login](#running-the-login)
    * [Developing](#developing)
    * [Testing](#testing)
* [License](#license)

# Getting Started

## Dependencies

What you need to run this login:
* `node` and `npm` (Use [NVM](https://github.com/creationix/nvm))
* Ensure you're running Node (`v4.1.x`+) and NPM (`2.14.x`+)

## Installing

* `fork` this repo
* `clone` your fork
* `npm install` to install all dependencies

## Running the login

After you have installed all dependencies you can now run the login with:
```bash
npm start
```

It will start a local server using `webpack-dev-server` which will watch, build (in-memory), and reload for you. The port will be displayed to you as `http://localhost:8080`.

## Developing

### Build files

* single run: `npm run build`
* build files and watch: `npm start`

## Testing

#### 1. Unit Tests

* single run: `npm test`
* live mode (TDD style): `npm run test-watch`

# License

[MIT](/LICENSE)
