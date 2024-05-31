function adminSigninForm() {
   console.log('adminSigninForm()');
   
   let form = document.admin_signin_form;
   if (form.a_id.value === '') {
      alert('INPUT ADMIN ID!!');
      form.a_id.focus();
      
   } else if (form.a_pw.value === '') {
      alert('INPUT ADMIN PW!!');
      form.a_pw.focus();
      
   } else {
      form.submit();
      
   }
   
}