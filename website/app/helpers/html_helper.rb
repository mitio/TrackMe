module HtmlHelper
  def label_row(title, content = nil)
    if block_given?
      content = capture { yield }
    end
    html = %Q{<tr class="#{cycle :even, :odd}">
      <th>#{title}</th>
      <td>#{content}</td>
    </tr>}
    block_given? ? concat(html) : html
  end

  def labels(content = nil)
    if block_given?
      content = capture { yield }
    end
    html = %Q{<table border="0" cellspacing="0" class="labels">
      #{content}
    </table>}
    block_given? ? concat(html) : html
  end
end
