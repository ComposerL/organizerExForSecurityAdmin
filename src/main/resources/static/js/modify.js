function adminModifyForm() {
   console.log('adminModifyForm()');
   
   let form = document.admin_modify_form;
   if (form.a_pw.value === '') {
      alert('INPUT ADMIN PW!!');
      form.a_pw.focus();
      
   } else if (form.a_mail.value === '') {
      alert('INPUT ADMIN MAIL!!');
      form.a_mail.focus();
      
   } else if (form.a_phone.value === '') {
      alert('INPUT ADMIN PHONE!!');
      form.a_phone.focus();
      
   } else {
      form.submit();
      
   }
   
}

function adminDeleteConfirm() {
   console.log('adminDeleteConfirm()');
   
   result = confirm('Are you sure you want to withdraw?');
   
   if (result) {
      location.href='/admin/adminDeleteConfirm';
   }
   
}