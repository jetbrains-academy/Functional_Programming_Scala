object FindTask {
  def isWhiteAndFluffy(cat: Cat): Boolean =
    cat.primaryColor == Color.White &&
      cat.furCharacteristics.contains(FurCharacteristic.Fluffy)

  // TODO: remove fluffy, use something else, which does not involve `contains`
  def findWhiteAndFluffyCat(bagOfCats: Set[Cat]): Option[Cat] =
    bagOfCats.find(isWhiteAndFluffy)

  def isCalicoAndAbyssinian(cat: Cat): Boolean =
    cat.breed == Breed.Abyssinian &&
      cat.pattern == Pattern.Tricolor(TricolorSubtype.Calico)

  def findCalicoAndAbyssinian(bagOfCats: Set[Cat]): Option[Cat] =
    bagOfCats.find(isCalicoAndAbyssinian)
}