object FilterTask {
  // Checks if the cat is calico.
  def isCatCalico(cat: Cat): Boolean =
    cat.pattern == Pattern.Tricolor(TricolorSubtype.Calico)

  // TODO: remove fluffy, use something else, which does not involve `contains`
  // Checks if the cat is fluffy.
  def isCatFluffy(cat: Cat): Boolean =
    cat.furCharacteristics.contains(FurCharacteristic.Fluffy)

  // Checks if the cat's breed is Abyssinian.
  def isCatAbyssinian(cat: Cat): Boolean =
    cat.breed == Breed.Abyssinian

  // Checks if the cat is calico, fluffy or Abyssinian.
  def desiredKindOfCat(cat: Cat): Boolean =
    isCatCalico(cat) || isCatFluffy(cat) || isCatAbyssinian(cat)

  // Filter the cats with the desired characteristics.
  // Notice that a named function is passed into the filter.
  def filterCats(shelterCats: Set[Cat]): Set[Cat] =
    shelterCats.filter(desiredKindOfCat)
}