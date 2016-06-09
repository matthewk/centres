package spatutorial.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import mapdata.MapData
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.HTMLCanvasElement
import spatutorial.client.components.Bootstrap.Button
import spatutorial.shared._


object Map {

  val Map = ReactComponentB[MapProps]("Map")
    .render_P(p =>
        <.canvas(^.width := p.width, ^.height := p.height)
    )
    .domType[HTMLCanvasElement]
    .componentDidMount(scope => Callback {
      // access context of the canvas

      val ctx = scope.getDOMNode().getContext("2d").asInstanceOf[CanvasRenderingContext2D]

      ctx.fillStyle = "#ff0000"
      ctx.fillRect(0, 0, 300, 200)
    })
    .build

  def apply(props: MapProps) = Map(props)


  case class MapProps(name: String, data: MapData, width: Int = 400, height: Int = 200)
}

object MapInput {



  case class MapInputProps(item: Option[MapPoint])

  case class State(item: MapPoint)

  class InputBackEnd(t: BackendScope[MapInputProps, State]) {

    def render(p: MapInputProps, s: State) = {

      <.div(
        Map(Map.MapProps("Test Map", new MapData())),
        <.div(
        <.input(^.inputMode := "text", ^.value := s.item.long.value.toString, ^.onChange ==> updateLong),
        <.input(^.inputMode := "text", ^.value := s.item.lat.value.toString, ^.onChange ==> updateLat),
          <.button("go", ^.onClick --> submitForm(p,s))
        )
      )
    }

    def submitForm(props: MapInputProps, state: State): Callback = {
      // do something in here
      println(state.item.toString)
      Callback.empty
    }

    def updateLat(e: ReactEventI) = {
      val text = e.target.value
      // update TodoItem content
      if (text.length > 0){
        t.modState(s => s.copy(item = s.item.copy(lat = Latitude(text.toFloat))))
      } else {
        t.modState(s => s.copy(item = s.item.copy(lat = Latitude(0.0f))))
      }

    }

    def updateLong(e: ReactEventI) = {
      val text = e.target.value
      // update TodoItem content
      if (text.length > 0) {
        t.modState(s => s.copy(item = s.item.copy(long = Longitude(text.toFloat))))
      } else {
        t.modState(s => s.copy(item = s.item.copy(long = Longitude(0.0f))))
      }
    }
  }

  val MapInput = ReactComponentB[MapInputProps]("MapInput")
    .initialState_P(p => State(p.item.getOrElse(MapPoint(Longitude(0.0f), Latitude(0.0f)))))
    .renderBackend[InputBackEnd]
    .build

  def apply(props: MapInputProps) = MapInput(props)
}