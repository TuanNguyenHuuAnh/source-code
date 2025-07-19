import React from 'react';

class PaymentKeyin extends React.Component
{  
    constructor(props) {
        super(props);
        this.state = {
        };
        this.handleInputGivePointChange = this.handleInputGivePointChange.bind(this);
        this.handleInputData = this.handleInputData.bind(this);
    }
    handleInputData(event) {
        const target = event.target;
        const inputName = target.name;        
        //var inputValue = target.value;    
        var txtName= document.getElementById('txtName');
        var txtID= document.getElementById('txtID');
        var txtDOB = document.getElementById('txtDOB'); 
        var dateDOB=this.convertStringToDate(txtDOB.value);  
        if(target.id && target.id === 'txtDOB') 
        {
            dateDOB=this.convertStringToDate(target.value);  
            
            if(dateDOB ==='')
            {
                
                document.getElementById("dtPODOBID").value ="";  
                txtDOB.value ="";  
               
            }
            else  if(dateDOB ==='Invalid Date')
            {
                document.getElementById("dtPODOBID").value ="";  
                txtDOB.focus();  
            }
            else
            {
                document.getElementById("dtPODOBID").value =dateDOB;  
                txtDOB.value =target.value;  
            }                
        }      
        
        //alert(txtDOB.value.trim());
        if (txtName.value.trim()!== '' &&txtID.value.trim()   !== ''
        && txtID.value.trim().length>3 && txtDOB.value.trim()   !== ''
        && dateDOB!=="Invalid Date") {
            //alert(dateDOB);
            document.getElementById("btnPolicySearch").className = "btn btn-primary";
            document.getElementById("btnPolicySearch").disabled = false;     
        } else {
            document.getElementById("btnPolicySearch").className = "btn btn-primary disabled";
            document.getElementById("btnPolicySearch").disabled = true;     
            
        }
    }
    validateInputData(event) {
     
        var txtName= document.getElementById('txtName');
        var txtID= document.getElementById('txtID');
        var txtDOB = document.getElementById('txtDOB');
        var dateDOB=this.convertStringToDate(txtDOB.value);  
        //alert(txtDOB.value.trim());
        if (txtName.value.trim()   !== '' &&txtID.value.trim()   !== '' 
        && txtDOB.value.trim()   !== ''&& dateDOB!=="Invalid Date") {
            document.getElementById("btnPolicySearch").className = "btn btn-primary";
            document.getElementById("btnPolicySearch").disabled = false;     
        } else {
            document.getElementById("btnPolicySearch").className = "btn btn-primary disabled";
            document.getElementById("btnPolicySearch").disabled = true;     
            
        }
    }
    convertDateToString=(value)=>
    {
      var date = new Date(value); // M-D-YYYY
      var d = date.getDate();
      var m = date.getMonth() + 1;
      var y = date.getFullYear();
      var dateString = (d <= 9 ? '0' + d : d) + '/' + (m <= 9 ? '0' + m : m) + '/' + y;
      return dateString;
    }
    convertStringToDate=(value)=>
    {
        var date ="";
        try {
            var  myArr  =    value.split("/");
            //alert(myArr);
            if(myArr && myArr.length===3)
            {
                var d = myArr[0];
                var m =  myArr[1];
                var y = myArr[2];
                date=y+"-"+m+"-"+d;   
                var date1 =new Date(date);
               
               if(date1 ==="Invalid Date"|| d.length>2 ||m<0||m>12||y<1900 || y>2100)
               {
                date="Invalid Date";
               }
               else{
                    if(this.convertDateToString(date1) ==="NaN/NaN/NaN")
                    {
                        date="Invalid Date";
                    }
                }
              
            }
            else{

                date="Invalid Date";
            }
        }
        catch(err)
        {
            date="Invalid Date";
        }
        return date;
     
    }
    handleInputGivePointChange(event) {
        const target = event.target;
        const inputName = target.name;        
        var inputValue = target.value;        
        if (target.id && target.id === 'dtPODOBID') {
          var d=new Date(document.getElementById("dtPODOBID").value);
          var dt=d.getDate();
          var mn=d.getMonth();
          mn++;
          var yy=d.getFullYear();
          //alert(target.value);
          document.getElementById("txtDOB").value=this.convertDateToString( target.value);
          //document.getElementById("txtDOB").value=dt+"/"+mn+"/"+yy
          document.getElementById("txtDOB").focus();        
          this.validateInputData();
        }
        else if (target.id && target.id === 'txtDOB') {
            //alert(document.getElementById("txtDOB").value);
        }
        else if (target.id && target.id === 'datepickericon') {
        }
        
    }
    convertStringFormatDate=(value)=>
    {
      var  myArr  =    value.split("/");
      if(myArr && myArr.length===3)
        {
            var d =  myArr[0];
            var m = myArr[1];
            var y = myArr[2];
            var dateString = ((d <= 9 && d.length===1)? '0' + d : d) + '/' + ((m <= 9 &&m.length===1)? '0' + m : m) + '/' + y;
            //alert(dateString);
            return dateString;
        }
        return value;        
    }
  render()
{     
    const mystyle = {
        color: "white",
        width: "50px",
        border: "none"
      };
        const callbackFamilyPayment = () => {  
            var txtName= document.getElementById('txtName');
            var txtID= document.getElementById('txtID');
            var dtPODOBID = document.getElementById('txtDOB');
            var policy=txtName.value.trim();
            if (txtName.value.trim()   !== '' &&txtID.value.trim()   !== '' && dtPODOBID.value.trim()   !== '') {               
                if(txtName.value.length<9)
                {
                  
                    let i=txtName.value.length;
                    for (i ; i < 9; i++) {
                        policy ='0'+policy;
                      }
                }  
                else
                {

                }            
                this.props.parentCallback(policy  ,txtID.value.trim()  ,this.convertStringFormatDate(dtPODOBID.value.trim()));
            }
        } 
        return(

                    <section className="sccard-warpper sccard-warpper-insurance-custom">
                        <h5 className="basic-bold">Vui lòng nhập thông tin hợp đồng cần thanh toán:</h5>
                        <div className="optionalform-wrapper optionalform-insurance-custom">
                            <div className="optionalform">
                            <div className="optionalform__body">
                                <div className="tab-wrapper">
                                <div className="tab">
                                    <div className="input">
                                    <div className="input__content">
                                        <input placeholder="Số hợp đồng" type="search" id='txtName' maxlength="9" onKeyPress={(event) => {
        if (!/[0-9]/.test(event.key)) {
          event.preventDefault();
        }
      }} onChange={this.handleInputData} required/>
                                    </div>
                                    <i><img src="img/icon/edit.svg" alt="" /></i>
                                    </div>
                                </div>
                                <div className="tab">
                                    <div className="input">
                                    <div className="input__content">
                                        <input placeholder="CMND/CCCD của bên mua bảo hiểm" type="search" id='txtID'  onChange={this.handleInputData}  required />
                                    </div>
                                    <i><img src="img/icon/edit.svg" alt="" /></i>
                                    </div>
                                </div>
                                <div className="tab">
                                    <div className="input">
                                    <div className="input__content">
                                        <input placeholder="Ngày sinh của bên mua bảo hiểm" type="search" id='txtDOB'   maxLength="10"  onChange={this.handleInputData}  onClick={this.handleInputGivePointChange} required />
                                        
                                    </div>
                                    <br/>
                                    <input type="date" name="PODOB"  style={mystyle} placeholder="DD/MM/YYYY" onfocus="(this.type='date')" id="dtPODOBID" onChange={this.handleInputGivePointChange} required>
                                            </input>
                                    </div>
                                </div>
                                </div>
                            </div>
                            </div>
                        </div>
                        <div className="bottom-btn">
                            <button className="btn btn-primary disabled"  id="btnPolicySearch" onClick={() => callbackFamilyPayment()} >Tiếp tục</button>
                            
                        </div>
                    </section>
      )
    }
  }


export default PaymentKeyin;