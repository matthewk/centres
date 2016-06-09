package spatutorial.shared

case class Longitude(value: Float) extends AnyVal
case class Latitude(value: Float) extends AnyVal

case class MapPoint(long: Longitude, lat: Latitude)