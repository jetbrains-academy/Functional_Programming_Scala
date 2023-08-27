class Task {
  //put your task here
}


// original code
trait AuthorizationStatus

case object Authorized   extends AuthorizationStatus
case object Unauthorized extends AuthorizationStatus

def authorize(userId: UserId): AuthorizationStatus = ...

// our extension
case object LoggedOut extends AuthorizationStatus

override def authorize(userId: UserId): AuthorizationStatus =
  if isLoggedOut(userId) then
    LoggedOut
  else
    super.authorize(userId)