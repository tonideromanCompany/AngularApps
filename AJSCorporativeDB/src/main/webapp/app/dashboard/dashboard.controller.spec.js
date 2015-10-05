/* jshint -W117, -W030 */
describe('DashboardController', function() {
    var controller;
    var people = mockData.getMockPeople();
    var events = mockData.getMockEvents();

    beforeEach(function() {
        bard.appModule('app.dashboard');
        bard.inject('$controller', '$log', '$q', '$rootScope', 'dataservice');
    });

    beforeEach(function () {
        sinon.stub(dataservice, 'getContacts').returns($q.when(contacts));
        sinon.stub(dataservice, 'getEvents').returns($q.when(events));
        sinon.stub(dataservice, 'getMessages').returns($q.when(messages));
        sinon.stub(dataservice, 'getAuth').returns($q.when(auth));
        controller = $controller('DashboardController');
        $rootScope.$apply();
    });

    bard.verifyNoOutstandingHttpRequests();

    describe('Dashboard controller', function() {
        it('should be created successfully', function () {
            expect(controller).to.be.defined;
        });

        describe('after activate', function() {
            it('should have title of Dashboard', function () {
                expect(controller.title).to.equal('Dashboard');
            });

            it('should have logged "Activated"', function() {
                expect($log.info.logs).to.match(/Activated/);
            });

            it('should have news', function () {
                expect(controller.news).to.not.be.empty;
            });

            it('should have at least 1 person', function () {
                expect(controller.contacts).to.have.length.above(0);
            });

            it('should have people count of 5', function () {
                expect(controller.contacts).to.have.length(7);
            });
            it('should have at least 1 event', function () {
                expect(controller.events).to.have.length.above(0);
            });

            it('should have events count of 5', function () {
                expect(controller.events).to.have.length(7);
            });
            
            it('should have at least 1 message', function () {
                expect(controller.messages).to.have.length.above(0);
            });

            it('should have messages count of 5', function () {
                expect(controller.messages).to.have.length(7);
            });
            
            it('should have at least 1 user', function () {
                expect(controller.auth).to.have.length.above(0);
            });
        });
    });
});
