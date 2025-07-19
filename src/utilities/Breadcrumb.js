import React, {useEffect, useState} from 'react';

const BreadcrumbItem = ({label}) => (
    <div className="breadcrums__item">
        <p>{label}</p>
        <span>&gt;</span>
    </div>
);

const Breadcrumb = ({items, isMobile = false}) => {
    const [shouldShowBreadcrumb, setShouldShowBreadcrumb] = useState(false);

    useEffect(() => {
        if (!shouldShowBreadcrumb && isMobile) {
            setShouldShowBreadcrumb(false);
        } else {
            setShouldShowBreadcrumb(true);
        }
    }, [isMobile]);

    return (
        <div>
            {shouldShowBreadcrumb && <section className="scbreadcrums">
                <div className="container">
                    <div className="breadcrums basic-white">
                        {items.map((item, index) => (
                            <BreadcrumbItem key={index} label={item.label}/>
                        ))}
                    </div>
                </div>
            </section>}
        </div>
    );
};

export default Breadcrumb;
