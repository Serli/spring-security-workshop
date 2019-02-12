export default function AuthInterceptor($state) {
  return {
    responseError: (rejection) => {
      switch (rejection.status) {
        case 403 :
          document.location.href = "/";
          break;
      }
      return rejection;
    }
  }

}
