/* jshint -W117, -W030 */
describe('ProfileController', function() {
    var controller;

    beforeEach(function() {
        bard.appModule('app.profile');
        bard.inject('$controller', '$log', '$rootScope');
    });

    beforeEach(function () {
        controller = $controller('ProfileController');
        sinon.stub(dataservice, 'getProfile').returns($q.when(profile));
        $rootScope.$apply();
    });

    bard.verifyNoOutstandingHttpRequests();

    describe('Profile controller', function() {
        it('should be created successfully', function () {
            expect(controller).to.be.defined;
        });

        describe('after activate', function() {
            it('should have title of Profile', function() {
                expect(controller.title).to.equal('Profile');
            });

            it('should have logged "Activated"', function() {
                expect($log.info.logs).to.match(/Activated/);
            });
            
            it('should have at least 1 profile', function () {
                expect(controller.profile).to.have.length.above(0);
            });
            
        });
    });
});
