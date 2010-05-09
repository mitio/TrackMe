xml.instruct!
xml.root do
  xml.status @status if @status
  xml.message @message if @message
  xml << yield
end