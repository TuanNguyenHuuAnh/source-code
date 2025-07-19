import React, { useEffect, useRef, useState } from 'react';
import iconArrowLeft from '../img/icon_Left.svg';
import iconArrowRight from '../img/icon_Right.svg';
import iconArrowLeftActive from '../img/icon_Left_Active.svg';
import iconArrowRightActive from '../img/icon_Right_Active.svg';
import iconCloseWhite from "../img/icon/iconCloseWhite.svg";
import './ImageViewer.css';

const ImageModal = ({ images, currentImageIndex, onClose, onNext, onPrev }) => {
    const imageUrl = images[currentImageIndex]?.imgData; // Trích xuất imgData từ mảng images
    const modalRef = useRef(null);

    // Các hàm và useEffect giữ nguyên

    return (
        <div className="modal-overlay">
            <div className="modal-content" ref={modalRef}>
                <button onClick={onClose} className="close-button">
                    <img src={iconCloseWhite} alt="closebtn" />
                </button>
                <button onClick={onPrev} disabled={currentImageIndex === 0} className="modal-button prev-button">
                    <img src={currentImageIndex === 0 ? iconArrowLeft : iconArrowLeftActive} alt="icon-arrow-left" width={16} height={16} />
                </button>
                <img
                    src={imageUrl}
                    alt="Review Image"
                    style={{ width: '100%', height: '100%', objectFit: 'cover' }}
                />
                <button onClick={onNext} disabled={currentImageIndex === images.length - 1} className="modal-button next-button">
                    <img src={currentImageIndex === images.length - 1 ? iconArrowRight : iconArrowRightActive} alt="icon-arrow-right" width={16} height={16} />
                </button>
            </div>
        </div>
    );
};

const ImageList = ({ images, onImageClick }) => {
    return (
        <div style={{ display: 'flex', flexDirection: 'row', alignItems: 'center', flexWrap: 'wrap' }}>
            {images?.map((image, index) => ( // Sử dụng image thay vì imageUrl
                <img
                    key={index}
                    src={image.imgData} // Truyền imgData từ mỗi đối tượng trong mảng images
                    alt={`Image ${index}`}
                    style={{ width: '52px', height: '73px', margin: '5px', cursor: 'pointer' }}
                    onClick={() => onImageClick(index)}
                />
            ))}
        </div>
    );
};

const ImageViewerMap = ({ images }) => {
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
            <ImageList images={images} onImageClick={openModal} />

            {/* Render the modal conditionally */}
            {modalImageIndex !== null && (
                <ImageModal
                    images={images}
                    currentImageIndex={modalImageIndex}
                    onNext={nextImage}
                    onPrev={prevImage}
                    onClose={closeModal}
                />
            )}
        </div>
    );
};

export default ImageViewerMap;
