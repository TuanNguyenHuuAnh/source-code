import React, {useEffect, useRef, useState} from 'react';
import iconArrowLeft from '../img/icon_Left.svg';
import iconArrowRight from '../img/icon_Right.svg';
import iconArrowLeftActive from '../img/icon_Left_Active.svg';
import iconArrowRightActive from '../img/icon_Right_Active.svg';
import iconCloseWhite from "../img/icon/iconCloseWhite.svg";
import './ImageViewer.css';

const ImageModal = ({images, currentImageIndex, onClose, onNext, onPrev}) => {
    const imageUrl = images[currentImageIndex];
    const modalRef = useRef(null);

    const handleBlur = (event) => {
        if (modalRef.current && !modalRef.current.contains(event.target)) {
            onClose();
        }
    };

    useEffect(() => {
        document.addEventListener('mousedown', handleBlur);
        return () => {
            document.removeEventListener('mousedown', handleBlur);
        };
    }, []);

    const handleKeyDown = (event) => {
        if (event.keyCode === 37) { // Phím mũi tên trái
            onPrev();
        } else if (event.keyCode === 39) { // Phím mũi tên phải
            onNext();
        }
    };

    useEffect(() => {
        window.addEventListener('keydown', handleKeyDown);
        return () => {
            window.removeEventListener('keydown', handleKeyDown);
        };
    }, []); 


    return (
        <div className="modal-overlay">
            <div className="modal-content" ref={modalRef}>
                <button onClick={onClose} className="close-button">
                    <img src={iconCloseWhite} alt="closebtn" />
                </button>
                <button onClick={onPrev} disabled={currentImageIndex === 0} className="modal-button prev-button">
                    <img src={currentImageIndex === 0 ? iconArrowLeft : iconArrowLeftActive} alt="icon-arrow-left" width={16} height={16}/>
                </button>
                <img
                    src={imageUrl}
                    alt="Review Image"
                    style={{width: '100%', height: '100%', objectFit: 'cover'}}
                />
                <button onClick={onNext} disabled={currentImageIndex === images.length - 1} className="modal-button next-button">
                    <img src={currentImageIndex === images.length - 1 ? iconArrowRight : iconArrowRightActive} alt="icon-arrow-right" width={16} height={16}/>
                </button>
            </div>
        </div>

    );
};

const ImageList = ({images, onImageClick}) => {
    return (
        <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center'}}>
            {images.map((imageUrl, index) => (
                <img
                    key={index}
                    src={imageUrl}
                    alt={`Image ${index}`}
                    style={{width: '52px', height: '73px', margin: '5px', cursor: 'pointer'}}
                    onClick={() => onImageClick(index)}
                />
            ))}
        </div>
    );
};

const ImageViewer = ({imageList}) => {
    const [modalImageIndex, setModalImageIndex] = useState(null);
    const openModal = (index) => {
        setModalImageIndex(index);
    };

    const closeModal = () => {
        setModalImageIndex(null);
    };

    const nextImage = () => {
        setModalImageIndex((prevIndex) => prevIndex + 1);
    };

    const prevImage = () => {
        setModalImageIndex((prevIndex) => prevIndex - 1);
    };

    return (
        <div>
            <ImageList images={imageList} onImageClick={openModal}/>

            {/* Render the modal conditionally */}
            {modalImageIndex !== null && (
                <ImageModal
                    images={imageList}
                    currentImageIndex={modalImageIndex}
                    onNext={nextImage}
                    onPrev={prevImage}
                    onClose={closeModal}
                />
            )}
        </div>
    );
};

export default ImageViewer;