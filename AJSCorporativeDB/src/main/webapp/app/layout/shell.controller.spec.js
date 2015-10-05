/* jshint -W117, -W030 */
describe('ShellController', function() {
    var controller;

    beforeEach(function() {
        bard.appModule('app.layout');
        bard.inject('$controller', '$q', '$rootScope', '$timeout', 'dataservice');
    });

    beforeEach(function () {
    	sinon.stub(dataservice, 'getAuth').returns($q.when(auth));
        controller = $controller('ShellController');
        $rootScope.$apply();
    });

    bard.verifyNoOutstandingHttpRequests();

    describe('Shell controller', function() {
        it('should be created successfully', function () {
            expect(controller).to.be.defined;
        });

        it('should show splash screen', function () {
            expect($rootScope.showSplash).to.be.true;
        });

        it('should hide splash screen after timeout', function (done) {
            $timeout(function() {
                expect($rootScope.showSplash).to.be.false;
                done();
            }, 1000);
            $timeout.flush();
        });
        
        it('should show page', function () {
            expect($rootScope.Logged).to.be.true;
        });
        
        it('should have at least 1 user', function () {
            expect(controller.auth).to.have.length.above(0);
        });
    });
});
