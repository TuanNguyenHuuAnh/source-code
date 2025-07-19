

function ShareZaloButton({clsName}) {
    // let htm = "<a class=\"zalo-share-button\" data-href=" + window.location.href +" data-oaid=\"3686213823440815036\" data-layout=\"2\" data-customize=" + true + " ></a>";
    let htm = "<a id=\"zalo-ref\" class=\"" + clsName + " zalo-share-button\" data-href=" + window.location.href +" data-oaid=\"3686213823440815036\" data-layout=\"2\" data-color=\"blue\" data-customize=\"true\"></a>";
    return <div dangerouslySetInnerHTML={{__html: htm}} />;
}

export default ShareZaloButton;
