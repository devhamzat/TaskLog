export const validateEmail = (email) => {
    const emailregex =
        /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return emailregex.test(email);
};

export const validateUserName = (userName) => {
    const userNameRegex = /^[A-Za-z\s]+$/;
    return userNameRegex.test(lastName);
}
export const validatePassword  = (password)=>{
    const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*]).*$/;
  return passwordRegex.test(password);
}
export const checkConfirmPassword =(confirmPassword)=>{
    let valid = false;
    const confirmPassword = confirmPasswordInput.value.trim();
    const password = passwordInput.value.trim();

    if (!isRequired(confirmPassword)) {
        showError(confirmPasswordInput, 'Please enter the password again');
    } else if (password !== confirmPassword) {
        showError(confirmPasswordInput, 'The password does not match');
    } else {
        showSuccess(confirmPasswordInput);
        valid = true;
    }

    return valid;
};