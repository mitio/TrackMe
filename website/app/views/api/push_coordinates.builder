xml.track do
  xml.id @track.id
  xml.name @track.name
  xml.coordinates_count @track.coordinates.size
end