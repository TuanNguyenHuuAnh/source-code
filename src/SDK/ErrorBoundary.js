import { Component } from "react";
import {trackingEvent, getSession, getUrlParameter} from './sdkCommon';
import {SUB_MENU_NAME} from './sdkConstant';

class ErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }
 
  static getDerivedStateFromError(error) {
    // Update state so the next render will show the fallback UI.
    return {
      hasError: true,
      error,
    };
  }
 
  componentDidCatch(error, errorInfo) {
    // You can also log the error to an error reporting service like AppSignal
    // logErrorToMyService(error, errorInfo);
    let from = getUrlParameter("fromApp");
    trackingEvent(getSession(SUB_MENU_NAME), getSession(SUB_MENU_NAME), getSession(SUB_MENU_NAME), from, 'Error: ' + getSession(SUB_MENU_NAME), error);
    console.log('Error: ', getSession(SUB_MENU_NAME));
    console.log('Error detail: ', error);
  }
 
  render() {
    const { hasError, error } = this.state;
 
    if (hasError) {
      // You can render any custom fallback UI
      return (
        <></>
      );
    }
 
    return this.props.children;
  }
}
 
export default ErrorBoundary;