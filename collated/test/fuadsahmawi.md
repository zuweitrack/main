# fuadsahmawi
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void addReminder(Reminder reminder) throws DuplicateReminderException {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<Reminder> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteReminder(Reminder target) throws ReminderNotFoundException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
