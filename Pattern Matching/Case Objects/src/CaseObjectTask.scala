object CaseObjectTask:
  def findDeltaCoord(bot: Bot, coordinates: Coordinates): Int =
    bot match
      case TurtleBot => 1
      case HareBot => 3
      case _ => 0

  def move(bot: Bot, direction: Direction, coordinates: Coordinates): Coordinates =
    val delta = findDeltaCoord(bot, coordinates)
    val newCoordinates = direction match
      case North => Coordinates(coordinates.x, coordinates.y + delta)
      case West => Coordinates(coordinates.x + delta, coordinates.y)
      case South => Coordinates(coordinates.x, coordinates.y - delta)
      case East => Coordinates(coordinates.x - delta, coordinates.y)
    newCoordinates
