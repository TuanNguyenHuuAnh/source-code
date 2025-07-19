import React, { useState, useEffect } from 'react';
import { Worker, Viewer, SpecialZoomLevel } from '@react-pdf-viewer/core';
import '@react-pdf-viewer/core/lib/styles/index.css';
import { toolbarPlugin } from '@react-pdf-viewer/toolbar';
import '@react-pdf-viewer/toolbar/lib/styles/index.css';
import { FE_BASE_URL } from './sdkConstant';
import ErrorBoundary from './ErrorBoundary';

const PdfViewer = ({ pdfUrl, mimeType, initialPage = 1, permission='VIEW', closePdf, hideBackButton, title }) => {
    const [error, setError] = useState('');
    const toolbarPluginInstance = toolbarPlugin();

    const handleContextMenu = (event) => {
        event.preventDefault();
    };

    const transform = (slot) => ({
        ...slot,
        Download: () => <></>,
        Open: () => <></>,
        Print: () => <></>,
        // GoToFirstPage: () => <></>,
        // GoToLastPage: () => <></>,
        ShowProperties: () => <></>,
        SwitchTheme: () => <></>,
        // GoToNextPage: () => <></>,
        // GoToPreviousPage: () => <></>,
        ShowSearchPopover: () => <></>,
        EnterFullScreen: () => <></>,
        // GoToFirstPageMenuItem: () => <></>,
        // GoToLastPageMenuItem: () => <></>,
        DownloadMenuItem: () => <></>,
        OpenMenuItem: () => <></>,
        PrintMenuItem: () => <></>,
        ShowPropertiesMenuItem: () => <></>,
        SwitchThemeMenuItem: () => <></>,
        SwitchScrollModeMenuItem: () => <></>,
        SwitchSelectionModeMenuItem: () => <></>,
        SwitchViewModeMenuItem: () => <></>,
        EnterFullScreenMenuItem: () => <></>,
    });

    const transformDownload = (slot) => ({
        ...slot,
        // Download: () => <></>,
        Open: () => <></>,
        Print: () => <></>,
        // GoToFirstPage: () => <></>,
        // GoToLastPage: () => <></>,
        ShowProperties: () => <></>,
        SwitchTheme: () => <></>,
        // GoToNextPage: () => <></>,
        // GoToPreviousPage: () => <></>,
        ShowSearchPopover: () => <></>,
        EnterFullScreen: () => <></>,
        // GoToFirstPageMenuItem: () => <></>,
        // GoToLastPageMenuItem: () => <></>,
        // DownloadMenuItem: () => <></>,
        OpenMenuItem: () => <></>,
        PrintMenuItem: () => <></>,
        ShowPropertiesMenuItem: () => <></>,
        SwitchThemeMenuItem: () => <></>,
        SwitchScrollModeMenuItem: () => <></>,
        SwitchSelectionModeMenuItem: () => <></>,
        SwitchViewModeMenuItem: () => <></>,
        EnterFullScreenMenuItem: () => <></>,
    });

    const { renderDefaultToolbar, Toolbar } = toolbarPluginInstance;

    const renderToolbar=(transform) => {
        try {
            return renderDefaultToolbar(transform)
        } catch (error) {
      // alert(error);
            return <></>
        }
    }

    useEffect(() => {
        const handleKeyDown = (event) => {
            if ((event.ctrlKey || event.metaKey) && (event.key === 's' || event.key === 'p')) {
                event.preventDefault();
            }
        };

        window.addEventListener('keydown', handleKeyDown);
        document.addEventListener('contextmenu', handleContextMenu);
        return () => {
            window.removeEventListener('keydown', handleKeyDown);
            document.removeEventListener('contextmenu', handleContextMenu);
        };
    }, [pdfUrl, mimeType, title]);

    return (
        <>
            <main style={{ width: '100%', marginTop: '0', paddingTop: '0' }}>
                {!hideBackButton && 
                (title?(
                    <>
                    <div style={{ display: 'flex', background: 'linear-gradient(180deg, #d25540, #b53e3d 82.68%)' }}>
                        <i onClick={closePdf}><img src={`${FE_BASE_URL}/img/icoback.svg`} alt="Quay lại" className='viewer-back-title' style={{paddingLeft: '4px'}}/></i>
                        <p className='viewer-back-title'>{title}</p>
                    </div>
                    </>
                ): (
                    <div style={{ display: 'flex', margin: '8px', marginTop: '0', background: '#de181f' }}>
                        <i onClick={closePdf}><img src={`${FE_BASE_URL}/img/icon/back.svg`} alt="Quay lại" /></i>
                    </div>
                )

                )

                }
                {mimeType.indexOf('video') >= 0 ? (
                    <video
                        src={pdfUrl}
                        controls
                        width="100%"
                        height="600px"
                        style={{ border: 'none' }}
                    >
                    </video>
                ) : (
                    <div style={{ height: '720px' }}>

                        <Worker workerUrl={FE_BASE_URL + '/js/pdf.worker.min.js'}>
                            <div style={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
                                <Toolbar>{permission === 'VIEW'?renderToolbar(transform):renderToolbar(transformDownload)}</Toolbar>
                                <ErrorBoundary>
                                    <Viewer
                                        fileUrl={pdfUrl}
                                        plugins={[toolbarPluginInstance]}
                                        initialPage={initialPage - 1}
                                        onContextMenu={handleContextMenu}
                                        onError={(error) => {
                                            setError('Failed to load PDF. Please check the file and try again.');
                                            console.error('Error loading PDF:', error);
                                        }}
                                    />
                                </ErrorBoundary>
                            </div>
                        </Worker>
                    </div>
                )}
            </main>
        </>
    );
};

export default PdfViewer;
