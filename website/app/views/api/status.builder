xml.session_status @session_status
xml.user do
  xml.id @user.id
  xml.name @user.login
  xml.email @user.email
end
