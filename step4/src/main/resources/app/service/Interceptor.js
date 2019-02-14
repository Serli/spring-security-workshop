export function csrfInterceptor($location, $state) {
    return {
        responseError: (rejection) => {
            switch (rejection.status) {
            }
            return rejection;
        }

    };
}

export default "XsrfInterceptor";