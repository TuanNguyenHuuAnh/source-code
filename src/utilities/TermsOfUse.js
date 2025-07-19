import React, {useEffect} from 'react';

const TermsOfUse = ({isMobile, label = ""}) => {
    useEffect(() => {
        if (isMobile) {
            var fixedElement = document.querySelectorAll(".fixed-mobile-app");

            fixedElement.forEach(function (fixedElement) {
                fixedElement.style.position = "fixed";
                fixedElement.style.top = 0;
                fixedElement.style.zIndex = 1;
            });
        }
    }, [isMobile]);

    return (
        <section className="scdieukhoan fixed-mobile-app">
            <div className="container">
                {label &&
                    <h1>{label}</h1>
                }
            </div>
        </section>
    );
};

export default TermsOfUse;
