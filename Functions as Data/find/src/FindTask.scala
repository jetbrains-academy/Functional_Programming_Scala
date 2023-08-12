object FindTask {
  def isWhiteAndFluffy(cat: Cat): Boolean =
    cat.primaryColor == Color.White &&
      cat.furCharacteristics.contains(FurCharacteristic.Fluffy)

  def findWhiteAndFluffyCat(bagOfCats: Set[Cat]): Option[Cat] =
    bagOfCats.find(isWhiteAndFluffy)

  def isCalicoAndPersian(cat: Cat): Boolean =
    cat.breed == Breed.Persian &&
      cat.pattern == Pattern.Tricolor(TricolorSubtype.Calico)

  def findCalicoAndPersian(bagOfCats: Set[Cat]): Option[Cat] =
    bagOfCats.find(isCalicoAndPersian)
}